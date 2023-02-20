package com.project.port.mapper;

import com.project.port.dto.PilotClientResponse;
import com.project.port.dto.PilotDto;
import com.project.port.dto.PilotResponse;

public interface PilotMapper {

  PilotDto clientResponseToDto(PilotClientResponse response);

  PilotResponse dtoToResponse(PilotDto dto);
}
