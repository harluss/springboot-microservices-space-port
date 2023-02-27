package com.project.port.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

  private static final String ERROR_MESSAGE = "Oh no!";

  private static final String GENERIC_ERROR_MESSAGE = "Oops, something went wrong. Please try again later";

  private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setUp() {
    globalExceptionHandler = new GlobalExceptionHandler();
  }

  @Test
  void handleNotFound() {
    final NotFoundException notFoundException = new NotFoundException(ERROR_MESSAGE);

    final ResponseEntity<ErrorResponse> errorResponse = globalExceptionHandler.handleNotFound(notFoundException);

    assertExceptionHandledCorrectly(errorResponse, ERROR_MESSAGE, HttpStatus.NOT_FOUND);
  }

  @Test
  void handleUncaught() {
    final RuntimeException runtimeException = new RuntimeException(ERROR_MESSAGE);

    final ResponseEntity<ErrorResponse> errorResponse = globalExceptionHandler.handleUncaught(runtimeException);

    assertExceptionHandledCorrectly(errorResponse, GENERIC_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private void assertExceptionHandledCorrectly(final ResponseEntity<ErrorResponse> res,
                                               final String message,
                                               final HttpStatus status) {

    assertThat(res.getStatusCode()).isEqualTo(status);
    assertThat(res.getBody()).isNotNull();
    assertThat(res.getBody().getMessage()).isEqualTo(message);
    assertThat(res.getBody().getError()).isEqualTo(status.name());
    assertThat(res.getBody().getStatus()).isEqualTo(status.value());
  }
}
