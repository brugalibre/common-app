package com.brugalibre.common.http.service;

import com.brugalibre.common.http.model.method.HttpMethod;
import com.brugalibre.common.http.model.request.HttpRequest;
import com.brugalibre.common.http.model.response.ResponseWrapper;
import com.brugalibre.common.http.service.response.ResponseReader;
import com.brugalibre.common.http.util.RequestUtil;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static com.brugalibre.common.http.auth.AuthConst.AUTHORIZATION;

/**
 * {@link HttpService} acts as a wrapper to an actual implementation of a http-client. So all http-calls are delegated to this
 * implementation. Currently, a {@link OkHttpClient} is used for calling any http- {@link Request}s
 *
 * @author Dominic
 */
public class HttpService {

   private static final Logger LOG = LoggerFactory.getLogger(HttpService.class);
   public static final String APPLICATION_JSON = "application/json";
   private final OkHttpClient okHttpClient;

   /**
    * Creates a new {@link HttpService} without any header interceptors
    */
   public HttpService(int timeOut) {
      this(timeOut, chain -> chain.proceed(chain.request()));
   }

   /**
    * Creates a new {@link HttpService} with the given header {@link Interceptor}
    *
    * @param interceptor {@link Interceptor} which allows to apply additional information such as headers to any request
    */
   public HttpService(int timeOut, Interceptor interceptor) {
      this.okHttpClient = buildOkHttpClient(timeOut, interceptor);
   }

   /**
    * Makes a get request to the given url and reads the result with the given {@link ResponseReader}
    * Any Exception which occurs will also be mapped by the provided {@link ResponseReader}.
    * The Result will be wrapped into a {@link ResponseWrapper}
    *
    * @param reader      the {@link ResponseReader} which reads and maps the received result
    * @param httpRequest the {@link HttpRequest} to send
    * @param <T>         defines the exact type of result
    * @return the received {@link Response}-body which is wrapped into a {@link ResponseWrapper}
    */
   public <T> ResponseWrapper<T> callRequestAndParse(ResponseReader<T> reader, HttpRequest httpRequest) {
      Request request = buildRequest(httpRequest);
      return callRequestAndParseInternal(reader, httpRequest.url(), request);
   }

   /**
    * Makes a get request to the given url and reads the result with the given {@link ResponseReader}
    * Any Exception which occurs will also be mapped by the provided {@link ResponseReader}
    *
    * @param reader      the {@link ResponseReader} which reads and maps the received result
    * @param httpRequest the {@link HttpRequest} to send
    * @param <T>         defines the exact type of result
    * @return the received {@link Response}-body which is wrapped into a {@link ResponseWrapper}
    */
   public <T> T callRequestParseAndUnwrap(ResponseReader<T> reader, HttpRequest httpRequest) {
      ResponseWrapper<T> responseWrapper = callRequestAndParse(reader, httpRequest);
      return responseWrapper.httpResponse();
   }

   private <T> ResponseWrapper<T> callRequestAndParseInternal(ResponseReader<T> reader, String url, Request request) {
      LOG.info("Call request {}", RequestUtil.toString(request));
      try (Response response = okHttpClient.newCall(request).execute()) {
         return reader.readResponse(response);
      } catch (Exception e) {
         LOG.error("Error while processing request {}!", request, e);
         return reader.createErrorResponse(e, url);
      }
   }

   private static Request buildRequest(HttpRequest httpRequest) {
      if (httpRequest.httpMethod() == HttpMethod.GET) {
         return new Request.Builder()
                 .url(httpRequest.url())
                 .header(AUTHORIZATION, httpRequest.authorization())
                 .build();
      }
      RequestBody body = RequestBody.create(httpRequest.jsonBody(), MediaType.parse(APPLICATION_JSON));
      return new Request.Builder()
              .url(httpRequest.url())
              .header("Accept", APPLICATION_JSON)
              .header("Content-Type", APPLICATION_JSON)
              .header(AUTHORIZATION, httpRequest.authorization())
              .method(httpRequest.httpMethod().name(), body)
              .build();
   }

   private OkHttpClient buildOkHttpClient(int timeOut, Interceptor interceptor) {
      return new OkHttpClient().newBuilder()
              .readTimeout(timeOut, TimeUnit.SECONDS)
              .addInterceptor(interceptor)
              .build();
   }
}
