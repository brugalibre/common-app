package com.brugalibre.util.file.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
   private JsonUtil() {
      // private
   }

   /**
    * Creates a Json-String from the given object, using the {@link DeserializationFeature#USE_JAVA_ARRAY_FOR_JSON_ARRAY}
    * and the JsonGenerator.Feature.ESCAPE_NON_ASCII
    *
    * @param object the object to create a Json-String from
    * @return the created {@link String}
    */
   public static String createJsonFromObject(Object object) {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
      objectMapper.getFactory().configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
      try {
         return objectMapper.writeValueAsString(object);
      } catch (JsonProcessingException e) {
         throw new IllegalStateException(e);
      }
   }
}
