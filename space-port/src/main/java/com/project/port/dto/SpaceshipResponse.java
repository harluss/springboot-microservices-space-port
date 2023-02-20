package com.project.port.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class SpaceshipResponse {

  private UUID id;

  private String name;

  @JsonProperty("class")
  private String classType;

  private Integer maxSpeed;

  private Integer payload;

  private Integer crew;

//  private List<UUID> crewList;

  private List<PilotResponse> crewList;

  private List<String> armament;
}
