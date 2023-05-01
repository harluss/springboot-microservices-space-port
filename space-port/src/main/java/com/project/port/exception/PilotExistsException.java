package com.project.port.exception;

public class PilotExistsException extends RuntimeException {

  public PilotExistsException(final String message) {
    super(message);
  }
}
