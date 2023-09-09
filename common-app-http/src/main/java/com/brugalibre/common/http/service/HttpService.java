package com.brugalibre.common.http.service;

import com.brugalibre.common.http.model.method.HttpMethod;
import com.brugalibre.common.http.model.request.HttpRequest;
import com.brugalibre.common.http.model.response.ResponseWrapper;
import com.brugalibre.common.http.service.response.ResponseReader;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Base64;
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
   private String credentials;

   /**
    * Creates a new {@link HttpService} without any credentials
    */
   public HttpService(int timeOut) {
      this.okHttpClient = buildOkHttpClient(timeOut);
      this.credentials = "not-set";
   }

   /**
    * Defines the credentials for this {@link HttpService} with the provided credentials
    *
    * @param credentials the credentials
    */
   public void setCredentials(String credentials) {
      this.credentials = credentials;
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
      LOG.info("Call request {}", request);
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
                 .build();
      }
      RequestBody body = RequestBody.create(httpRequest.jsonBody(), MediaType.parse(APPLICATION_JSON));
      return new Request.Builder()
              .url(httpRequest.url())
              .header("Accept", APPLICATION_JSON)
              .header("Content-Type", APPLICATION_JSON)
              .method(httpRequest.httpMethod().name(), body)
              .build();
   }

   private OkHttpClient buildOkHttpClient(int timeOut) {
      return new OkHttpClient().newBuilder()
              .readTimeout(timeOut, TimeUnit.SECONDS)
              .addInterceptor(getAuthorizationInterceptor())
              .build();
   }

   private Interceptor getAuthorizationInterceptor() {
      return chain -> {
         Request request = chain.request();
         return chain.proceed(request.newBuilder()
                 .header(AUTHORIZATION, credentials)
                 .build());
      };
   }
}
