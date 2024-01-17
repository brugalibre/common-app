package com.brugalibre.common.http.model.request;

import com.brugalibre.common.http.model.method.HttpMethod;

public record HttpRequest(HttpMethod httpMethod, String jsonBody, String url, String authorization) {

   private static final String EMPTY_AUTHORIZATION = "";

   public static HttpRequest getHttpRequest(HttpMethod httpMethod, String url) {
      return new HttpRequest(httpMethod, null, url, EMPTY_AUTHORIZATION);
   }

   /**
    * Adds the given json-body to this {@link HttpRequest}
    *
    * @param jsonBody the json-body value
    * @return a copy of this {@link HttpRequest} adding the given json-body
    */
   public HttpRequest withJsonBody(String jsonBody) {
      return new HttpRequest(this.httpMethod, jsonBody, this.url, this.authorization);
   }

   /**
    * Adds the given authorization value to this {@link HttpRequest}
    *
    * @param authorization the authorization value
    * @return a copy of this {@link HttpRequest} adding the given authorization value
    */
   public HttpRequest withAuthorization(String authorization) {
      return new HttpRequest(this.httpMethod, this.jsonBody, this.url, authorization);
   }

   @Override
   public String toString() {
      return "HttpRequest{" +
              "httpMethod=" + httpMethod +
              ", jsonBody='" + jsonBody + '\'' +
              ", url='" + url + '\'' +
              ", authorization='#####'" +
              '}';
   }
}
