package com.project.port.dto.pilot;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class AddPilotClientRequest {

  private String name;

  private String species;

  private String profession;

  private List<String> weapons;
}
