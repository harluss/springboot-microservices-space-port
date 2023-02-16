package com.project.hangar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SpaceshipResponse {

  private UUID id;

  private String name;

  @JsonProperty("class")
  private String classType;

  private Integer payload;

  private Integer crew;
}
