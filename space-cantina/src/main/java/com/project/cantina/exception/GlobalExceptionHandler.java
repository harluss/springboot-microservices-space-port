package com.project.cantina.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final String VALIDATION_ERROR_MESSAGE = "Validation error. Check 'errors' field for details";

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ResponseEntity<ErrorResponse> handleNotFound(final NotFoundException exception) {

    final ErrorResponse errorResponse = buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {

    final ErrorResponse errorResponse = buildErrorResponse(HttpStatus.BAD_REQUEST, VALIDATION_ERROR_MESSAGE);
    exception.getFieldErrors().forEach(er -> errorResponse.addValidationError(er.getField(), er.getDefaultMessage()));

    return ResponseEntity.badRequest().body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ResponseEntity<ErrorResponse> handleUncaught(final Exception exception) {

    log.info(exception.getStackTrace());
    final ErrorResponse errorResponse = buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

    return ResponseEntity.internalServerError().body(errorResponse);
  }

  private ErrorResponse buildErrorResponse(final HttpStatus status, final String message) {

    return ErrorResponse.builder()
        .status(status.value())
        .error(status.name())
        .message(message)
        .build();
  }
}
