package com.brugalibre.common.http.service.response;

import com.brugalibre.common.http.model.response.ResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Base implementation of a {@link ResponseReader}. Takes the response body and maps it into a {@link ResponseWrapper}
 * Only this very subtype has to be provided by any subclass of this {@link AbstractHttpResponseReader}
 *
 * @param <T>
 * @author Dominic
 */
public abstract class AbstractHttpResponseReader<T> implements ResponseReader<T> {

   private static final Logger LOG = LoggerFactory.getLogger(AbstractHttpResponseReader.class);

   @Override
   public ResponseWrapper<T> readResponse(Response response) throws IOException {
      String bodyAsString = getResponseBody(response);
      ObjectMapper objectMapper = getObjectMapper();
      T httpResponse = readValue(bodyAsString, objectMapper);
      LOG.info("Got response {}, with body {}", response, bodyAsString);
      return ResponseWrapper.of(httpResponse, response.isSuccessful());
   }

   protected T readValue(String bodyAsString, ObjectMapper objectMapper) throws JsonProcessingException {
      if (isEmpty(bodyAsString)) {
         return null;
      }
      return objectMapper.readValue(bodyAsString, getResponseResultClass());
   }

   private static String getResponseBody(Response response) throws IOException {
      ResponseBody responseBody = response.body();
      return responseBody == null ? "" : responseBody.string();
   }

   @Override
   public ResponseWrapper<T> createErrorResponse(Exception e, String url) {
      return ResponseWrapper.error(e, url);
   }

   protected abstract Class<T> getResponseResultClass();

   private static ObjectMapper getObjectMapper() {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
      return objectMapper;
   }

   private static boolean isEmpty(String bodyAsString) {
      return bodyAsString.trim().length() == 0;
   }
}
