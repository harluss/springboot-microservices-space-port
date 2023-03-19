package com.project.port.dto.pilot;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Value
@Builder
@Jacksonized
public class AddPilotRequest {

  @NotBlank
  @Size(max = 150)
  private String name;

  @NotBlank
  @Size(max = 150)
  private String species;

  @NotBlank
  @Size(max = 150)
  private String profession;

  @Size(max = 10)
  private List<String> weapons;
}
