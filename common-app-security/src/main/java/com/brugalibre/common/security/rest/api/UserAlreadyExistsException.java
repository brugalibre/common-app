package com.brugalibre.common.security.rest.api;

public class UserAlreadyExistsException extends RuntimeException {
   public UserAlreadyExistsException(String message) {
      super(message);
   }
}
