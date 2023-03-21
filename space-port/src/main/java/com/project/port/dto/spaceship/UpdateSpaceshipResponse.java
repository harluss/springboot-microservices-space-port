package com.project.port.dto.spaceship;

import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import com.fasterxml.jackson.annotation.JsonProperty;

@Value
@Builder
@Jacksonized
public class UpdateSpaceshipResponse {

  private UUID id;

  private String name;

  @JsonProperty("class")
  private String classType;

  private Integer maxSpeed;

  private Integer payload;

  private List<UUID> crewIds;

  private List<String> armament;
}
