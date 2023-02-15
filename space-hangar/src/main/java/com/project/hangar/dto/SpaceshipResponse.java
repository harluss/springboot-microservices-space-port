package com.project.hangar.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpaceshipResponse {

  private Integer id;

  private String name;

  private Integer payload;

  private Integer crew;
}
