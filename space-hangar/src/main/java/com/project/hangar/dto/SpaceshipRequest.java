package com.project.hangar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.util.List;
import java.util.UUID;

@Value
@Builder
public class SpaceshipRequest {

  @NotBlank
  @Size(max = 150)
  private String name;

  @NotBlank
  @Size(max = 150)
  @JsonProperty("class")
  private String classType;

  @NotNull
  @Min(value = 0)
  @Max(value = 10000)
  private Integer payload;

  @NotNull
  @Min(value = 0)
  @Max(value = 10000)
  private Integer maxSpeed;

  @Size(min = 1, max = 5)
  private List<UUID> crewIds;

  @Size(max = 10)
  private List<String> armament;
}
