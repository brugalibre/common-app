package com.brugalibre.test.http;

import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.HttpStatusCode;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class DummyHttpServerTestCaseBuilder {

   private final int port;
   private final ClientAndServer clientServer;
   private final Map<HttpRequest, HttpResponse> requestHttpResponseMap;
   private String host;

   public DummyHttpServerTestCaseBuilder(int port) {
      this.clientServer = ClientAndServer.startClientAndServer(port);
      this.port = port;
      this.requestHttpResponseMap = new HashMap<>();
   }

   public DummyHttpServerTestCaseBuilder withHost(String host) {
      this.host = host;
      if (!this.host.contains(":")) {
         this.host = this.host + ":" + port;
      }
      return this;
   }

   public HttpRequestResponseBuilder withRequestResponse() {
      return new HttpRequestResponseBuilder(this);
   }

   public DummyHttpServerTestCaseBuilder build() {
      requireNonNull(host, "call 'withHost' first");
      for (Map.Entry<HttpRequest, HttpResponse> httpRequestHttpResponseEntry : requestHttpResponseMap.entrySet()) {
         clientServer.when(httpRequestHttpResponseEntry.getKey())
                 .respond(httpRequestHttpResponseEntry.getValue());
      }
      return this;
   }

   private void add(HttpRequestResponseBuilder httpRequestResponseBuilder) {
      this.requestHttpResponseMap.put(httpRequestResponseBuilder.httpRequest, httpRequestResponseBuilder.httpResponse);
   }

   public void showUI() {
      clientServer.openUI(TimeUnit.MINUTES, 60);
   }

   public void stop() {
      clientServer.stop();
   }

   public static final class HttpRequestResponseBuilder {

      private final DummyHttpServerTestCaseBuilder dummyHttpServerTestCaseBuilder;
      private String path;
      private String responseBody;
      private String method;
      private HttpRequest httpRequest;
      private HttpResponse httpResponse;
      private Header header;
      private String requestBody;
      private HttpStatusCode httpStatusCode;

      public HttpRequestResponseBuilder(DummyHttpServerTestCaseBuilder dummyHttpServerTestCaseBuilder) {
         this.httpStatusCode = HttpStatusCode.OK_200;
         this.dummyHttpServerTestCaseBuilder = dummyHttpServerTestCaseBuilder;
      }

      public HttpRequestResponseBuilder withPath(String path) {
         this.path = path;
         return this;
      }

      public HttpRequestResponseBuilder withMethod(String method) {
         this.method = method;
         return this;
      }

      public HttpRequestResponseBuilder withHttpStatusCode(HttpStatusCode httpStatusCode) {
         this.httpStatusCode = httpStatusCode;
         return this;
      }

      public HttpRequestResponseBuilder withResponseBody(String responseBody) {
         this.responseBody = responseBody;
         return this;
      }

      public HttpRequestResponseBuilder withRequestBody(String requestBody) {
         this.requestBody = requestBody;
         return this;
      }

      public HttpRequestResponseBuilder withHeader(Header header) {
         this.header = header;
         return this;
      }

      public DummyHttpServerTestCaseBuilder buildRequestResponse() {
         requireNonNull(path, "call 'withPath' first");
         httpRequest = new HttpRequest()
                 .withMethod(method)
                 .withBody(requestBody)
                 .withPath(path)
                 .withHeader(header);

         httpResponse = new HttpResponse()
                 .withStatusCode(httpStatusCode.code())
                 .withBody(responseBody);
         this.dummyHttpServerTestCaseBuilder.add(this);
         return dummyHttpServerTestCaseBuilder;
      }
   }

}
