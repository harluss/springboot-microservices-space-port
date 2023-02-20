package com.project.cantina.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Jacksonized
@Builder
public class PilotIdsRequest {

  private List<UUID> pilotUuids;
}
