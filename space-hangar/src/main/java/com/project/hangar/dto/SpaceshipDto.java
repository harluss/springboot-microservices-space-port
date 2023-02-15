package com.project.hangar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpaceshipDto {

  private Integer id;

  private String name;

  private Integer payload;

  private Integer crew;
}
