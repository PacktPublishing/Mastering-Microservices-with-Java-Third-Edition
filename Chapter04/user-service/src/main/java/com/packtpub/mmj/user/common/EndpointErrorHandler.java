package com.packtpub.mmj.user.common;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EndpointErrorHandler {

  private static final String UNEXPECTED_ERROR = "Exception.unexpected";
  private final MessageSource messageSource;

  @Autowired
  public EndpointErrorHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorInfo> handleRestaurantNotFoundException(HttpServletRequest request,
      UserNotFoundException ex, Locale locale) {
    ErrorInfo response = new ErrorInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage(messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale));
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(DuplicateUserException.class)
  public ResponseEntity<ErrorInfo> handleDuplicateRestaurantException(HttpServletRequest request,
      DuplicateUserException ex, Locale locale) {
    ErrorInfo response = new ErrorInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage(messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale));
    return new ResponseEntity<>(response, HttpStatus.IM_USED);
  }
  @ExceptionHandler(InvalidUserException.class)
  public ResponseEntity<ErrorInfo> handleInvalidRestaurantException(HttpServletRequest request,
      InvalidUserException ex, Locale locale) {
    ErrorInfo response = new ErrorInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage(messageSource.getMessage(ex.getMessage(), ex.getArgs(), locale));
    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorInfo> handleException(Exception ex, Locale locale) {
    String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);
    return new ResponseEntity<>(new ErrorInfo(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
