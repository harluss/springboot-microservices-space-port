package com.project.port.dto.spaceship;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@Jacksonized
public class SpaceshipClientResponse {

  private UUID id;

  private String name;

  @JsonProperty("class")
  private String classType;

  private Integer maxSpeed;

  private Integer payload;

  private List<UUID> crewIds;

  private List<String> armament;
}
