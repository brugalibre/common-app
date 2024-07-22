package com.brugalibre.util.file.json;

import java.io.IOException;

public class JsonObjectNotReadableException extends RuntimeException {
   public JsonObjectNotReadableException(IOException e) {
      super(e);
   }
}
