package com.project.hangar.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class SpaceshipDto {

  private UUID id;

  private String name;

  private String classType;

  private Integer maxSpeed;

  private Integer payload;

  private List<UUID> crewIds;

  private List<String> armament;
}
