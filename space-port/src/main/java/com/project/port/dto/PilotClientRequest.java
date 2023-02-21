package com.project.port.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class PilotClientRequest {

  private List<UUID> pilotIds;
}
