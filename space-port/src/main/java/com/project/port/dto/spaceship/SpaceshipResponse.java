package com.project.port.dto.spaceship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.port.dto.pilot.PilotResponse;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class SpaceshipResponse {

  private UUID id;

  private String name;

  @JsonProperty("class")
  private String classType;

  private Integer maxSpeed;

  private Integer payload;

  private List<PilotResponse> crew;

  private List<String> armament;
}
