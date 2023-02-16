package com.project.hangar.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SpaceshipDto {

  private UUID id;

  private String name;

  private String classType;

  private Integer payload;

  private Integer crew;
}
