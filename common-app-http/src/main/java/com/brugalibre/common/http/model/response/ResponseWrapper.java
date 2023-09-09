package com.brugalibre.common.http.model.response;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The {@link ResponseWrapper} wraps the actual response, read by the {@link ObjectMapper} and adds some generic
 * information like an exception which may have occurred and if the request was successful or not
 *
 * @param httpResponse the response data which was read by the {@link ObjectMapper}
 * @param exception    an exception if there was any
 * @param successful   a boolean indicating if the reading was successful according to the http-status
 * @param url          the url for which an exception occurred
 * @param <T>          the actual response type this wrapper wraps
 */
public record ResponseWrapper<T>(T httpResponse, boolean successful, Exception exception,
                                 String url) {
   /**
    * Creates a new {@link ResponseWrapper} for the given response value
    *
    * @param httpResponse the response value fetched by the rest-api
    * @param successful   <code>true</code>> if the http-status was 200 or <code>false</code> if not
    * @param <T>          the specific type of the response value
    * @return a new {@link ResponseWrapper} for the given response value
    */
   public static <T> ResponseWrapper<T> of(T httpResponse, boolean successful) {
      return new ResponseWrapper<>(httpResponse, successful, null, null);
   }

   /**
    * Creates a new {@link ResponseWrapper} for an exception which has occurred
    *
    * @param exception the {@link Exception}
    * @param url       the url for which an exception occurred
    * @param <T>the    specific type of the response value
    * @return a new {@link ResponseWrapper} for an exception which has occurred
    */
   public static <T> ResponseWrapper<T> error(Exception exception, String url) {
      return new ResponseWrapper<>(null, false, exception, url);
   }
}
