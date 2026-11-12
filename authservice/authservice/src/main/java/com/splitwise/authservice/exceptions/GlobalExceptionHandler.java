package com.splitwise.authservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

      @ExceptionHandler(EmailAlreadyExistsException.class)
      public ResponseEntity<String> handleEmailAlreadyExists(
              EmailAlreadyExistsException exception
      ){
          return ResponseEntity.status(HttpStatus.CONFLICT)
                  .body(exception.getMessage());
      }

      @ExceptionHandler
      public ResponseEntity<String> handleEmailNotFoundException(
              EmailNotFoundException exception
      ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(exception.getMessage());
      }


      @ExceptionHandler(BadCredentialsException.class)
      public ResponseEntity<String> handleBadCredentials(
              BadCredentialsException ex) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
      }
}
