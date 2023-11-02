package com.brugalibre.common.security.rest;

import com.brugalibre.common.security.i18n.TextResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@ControllerAdvice
public class AuthenticationControllerExceptionHandler {

   @ExceptionHandler(MethodArgumentNotValidException.class)
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult()
              .getAllErrors()
              .forEach(putError(errors));
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
              .body(errors);
   }

   @ExceptionHandler({NullPointerException.class, IllegalStateException.class, UserAlreadyExistsException.class})
   protected ResponseEntity<Object> handleOtherExceptions(Exception ex) {
      Map<String, String> errors = Map.of("error", ex.getLocalizedMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(errors);

   }

   @ExceptionHandler({BadCredentialsException.class})
   protected ResponseEntity<Object> handleBadCredentialsException() {
      // We catch the 'BadCredentialsException' to provide an alternative translation
      Map<String, String> errors = Map.of("error", TextResources.INVALID_CREDENTIALS);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(errors);
   }

   private static Consumer<ObjectError> putError(Map<String, String> errors) {
      return (error) -> {
         String fieldName = ((FieldError) error).getField();
         String errorMessage = error.getDefaultMessage();
         errors.put(fieldName, errorMessage);
      };
   }
}
