package com.project.port.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.port.exception.ErrorResponse;
import com.project.port.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

  private final ErrorDecoder defaultErrorDecoder = new Default();

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public Exception decode(final String s, final Response response) {

    final ErrorResponse errorResponse;

    try (final InputStream bodyIs = response.body().asInputStream()) {
      errorResponse = mapper.readValue(bodyIs, ErrorResponse.class);
    } catch (final IOException e) {
      return new Exception(e.getMessage());
    }

    /*
    Only intercepts 404 responses from clients to throw custom NotFoundException and pass the original message.
    Other responses are returned as normal and exceptions caused by 5XX responses are handled by GlobalExceptionHandler.
     */
    switch (response.status()) {
      case 404:
        log.info("DECODER 404: {}", response.request());
        return new NotFoundException(errorResponse.getMessage());
      default:
        log.error("DECODER default: {}", response.request());
        return defaultErrorDecoder.decode(s, response);
    }
  }
}
