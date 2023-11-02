package com.brugalibre.common.security.rest;

public class UserAlreadyExistsException extends RuntimeException {
   public UserAlreadyExistsException(String message) {
      super(message);
   }
}
