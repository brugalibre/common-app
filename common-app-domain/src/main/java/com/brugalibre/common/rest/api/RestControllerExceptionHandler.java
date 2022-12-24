package com.brugalibre.common.rest.api;

import com.brugalibre.domain.contactpoint.mobilephone.exception.PhoneNrNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@ControllerAdvice
public class RestControllerExceptionHandler {

   @ExceptionHandler(MethodArgumentNotValidException.class)
   protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult()
              .getAllErrors()
              .forEach(putError(errors));
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
              .body(errors);
   }

   @ExceptionHandler({PhoneNrNotValidException.class})
   protected ResponseEntity<Object> handleOtherExceptions(Exception ex) {
      Map<String, String> errors = Map.of("error", ex.getLocalizedMessage());
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
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
