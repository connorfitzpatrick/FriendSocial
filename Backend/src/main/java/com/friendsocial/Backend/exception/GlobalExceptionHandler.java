package com.friendsocial.Backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(ExpiredJwtException.class)
  public RedirectView handleExpiredJwtException(ExpiredJwtException ex, RedirectAttributes attributes) {
    // Perform any additional logging or handling if needed

    // Redirect to the login page
    RedirectView redirectView = new RedirectView("/login");
    redirectView.setStatusCode(HttpStatus.UNAUTHORIZED);
    System.out.println("FuCK this is weird handleExpiredJwtException no running");
    return redirectView;
  }
}
