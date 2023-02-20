package com.project.port.mapper;

import com.project.port.dto.PilotClientRequest;
import com.project.port.dto.PilotClientResponse;
import com.project.port.dto.PilotDto;
import com.project.port.dto.PilotResponse;

import java.util.List;
import java.util.UUID;

public interface PilotMapper {

  PilotDto clientResponseToDto(PilotClientResponse response);

  PilotResponse dtoToResponse(PilotDto dto);

  PilotClientRequest uuidsToClientRequest(List<UUID> uuids);

}
