package com.project.cantina.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class PilotResponse {

  private UUID id;

  private String name;

  private String species;

  private String profession;

  private List<String> weapons;
}
