package com.project.cantina.dto;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class PilotNamesRequest {

  private List<String> pilotNames;
}
