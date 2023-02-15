package com.project.hangar.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class SpaceshipResponse {

  private UUID id;

  private String name;

  private Integer payload;

  private Integer crew;
}
