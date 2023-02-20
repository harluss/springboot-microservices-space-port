package com.project.port.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class PilotResponse {

  private UUID id;

  private String name;

  private String species;

  private String profession;

  private List<String> weapons;
}
