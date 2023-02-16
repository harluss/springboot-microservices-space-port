package com.project.hangar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.*;

@Value
public class SpaceshipRequest {

  @NotBlank
  @Size(max = 150)
  private String name;

  @NotBlank
  @Size(max = 150)
  @JsonProperty("class")
  private String classType;

  @NotNull
  @Min(value = 0)
  @Max(value = 10000)
  private Integer payload;

  @NotNull
  @Min(value = 0)
  @Max(value = 10000)
  private Integer crew;
}
