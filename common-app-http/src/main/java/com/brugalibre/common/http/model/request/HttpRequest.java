package com.brugalibre.common.http.model.request;

import com.brugalibre.common.http.model.method.HttpMethod;

public record HttpRequest(HttpMethod httpMethod, String jsonBody, String url) {

   public static HttpRequest getHttpGetRequest(String url) {
      return new HttpRequest(HttpMethod.GET, null, url);
   }

   public static HttpRequest getHttpPostRequest(String jsonBody, String url) {
      return new HttpRequest(HttpMethod.POST, jsonBody, url);
   }
   public static HttpRequest getHttpRequest(HttpMethod httpMethod, String jsonBody, String url) {
      return new HttpRequest(httpMethod, jsonBody, url);
   }
}
