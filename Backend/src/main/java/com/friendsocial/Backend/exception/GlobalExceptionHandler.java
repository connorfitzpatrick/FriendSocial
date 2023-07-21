package com.friendsocial.Backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ExpiredJwtException.class)
  protected ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
    // Customize the error response when the token is expired
    String errorMessage = "Token expired: " + ex.getMessage();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
  }

  // Add other exception handling methods as needed
}