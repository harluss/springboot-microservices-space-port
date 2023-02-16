package com.project.hangar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class SpaceshipRequest {

  @NotBlank
  private String name;

  @NotBlank
  @JsonProperty("class")
  private String classType;

  @NotNull
  private Integer payload;

  @NotNull
  private Integer crew;
}
