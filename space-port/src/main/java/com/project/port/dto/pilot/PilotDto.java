package com.project.port.dto.pilot;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class PilotDto {

  private UUID id;

  private String name;

  private String species;

  private String profession;

  private List<String> weapons;
}
