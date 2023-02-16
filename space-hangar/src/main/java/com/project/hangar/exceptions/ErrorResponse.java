package com.project.hangar.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  private Integer status;

  private String error;

  private String message;

  private List<ValidationError> errors;

  @Data
  @Builder
  private static class ValidationError {

    private String field;

    private String message;
  }

  public void addValidationError(final String field, final String message) {
    if (Objects.isNull(errors)) {
      errors = new ArrayList<>();
    }

    errors.add(ValidationError.builder()
        .field(field)
        .message(message)
        .build());
  }
}
