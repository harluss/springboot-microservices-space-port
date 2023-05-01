package com.project.port.dto.spaceship;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class AddSpaceshipClientRequest {

  private String name;

  @JsonProperty("class")
  private String classType;

  private Integer payload;

  private Integer maxSpeed;

  private List<UUID> crewIds;

  private List<String> armament;
}
