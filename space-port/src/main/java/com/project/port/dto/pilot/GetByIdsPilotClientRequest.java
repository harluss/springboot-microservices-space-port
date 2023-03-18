package com.project.port.dto.pilot;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Value
@Builder
@Jacksonized
public class GetByIdsPilotClientRequest {

  private List<UUID> pilotIds;
}
