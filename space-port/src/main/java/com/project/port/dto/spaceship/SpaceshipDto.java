package com.project.port.dto.spaceship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.port.dto.pilot.PilotDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class SpaceshipDto {

  private UUID id;

  private String name;

  @JsonProperty("class")
  private String classType;

  private Integer maxSpeed;

  private Integer payload;

  private List<UUID> crewIds;

  private List<PilotDto> crew;

  private List<String> armament;
}
