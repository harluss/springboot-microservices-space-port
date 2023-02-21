package com.project.cantina.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class PilotIdsRequest {

  private List<UUID> pilotIds;
}
