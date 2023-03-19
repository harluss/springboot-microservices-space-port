package com.project.port.dto.spaceship;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.port.dto.pilot.AddPilotRequest;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
@Jacksonized
public class AddSpaceshipRequest {

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

  @Valid
  @Size(min = 1, max = 5)
  private List<AddPilotRequest> crew;

  @Size(max = 10)
  private List<String> armament;
}
