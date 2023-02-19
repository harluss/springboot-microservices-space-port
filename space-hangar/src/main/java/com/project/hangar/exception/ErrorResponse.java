package com.project.hangar.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

  private Integer status;

  private String error;

  private String message;

  private List<ValidationError> errors;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
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
