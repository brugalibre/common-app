package com.brugalibre.util.file.json;

import com.brugalibre.util.file.FileUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public class JsonService {
   private final ObjectMapper objectMapper;

   /**
    * Creates an {@link JsonService} with the given {@link ObjectMapper}
    *
    * @param objectMapper the ObjectMapper which is used to read json content
    */
   public JsonService(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
   }

   /**
    * Creates an {@link JsonService} with the default {@link ObjectMapper} which has the
    * {@link DeserializationFeature#ACCEPT_EMPTY_STRING_AS_NULL_OBJECT} flag set to <code>true</code>
    */
   public JsonService() {
      this(getObjectMapper());
   }

   /**
    * Loads the given json file and create a new class with the content provided by the file
    *
    * @param jsonFile the file
    * @param clazz    type of the class to create
    * @return a new instance of the given class, with the content of the given json-file
    */
   public <T> T readJson(String jsonFile, Class<T> clazz) {
      try (InputStream inputStream = FileUtil.getInputStream(jsonFile)) {
         return objectMapper.readValue(inputStream, clazz);
      } catch (IOException e) {
         throw new JsonObjectNotReadableException(e);
      }
   }

   private static ObjectMapper getObjectMapper() {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
      return objectMapper;
   }
}
