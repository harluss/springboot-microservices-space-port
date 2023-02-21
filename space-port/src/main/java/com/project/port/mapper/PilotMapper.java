package com.project.port.mapper;

import com.project.port.dto.PilotClientRequest;
import com.project.port.dto.PilotClientResponse;
import com.project.port.dto.PilotDto;
import com.project.port.dto.PilotResponse;

import java.util.List;
import java.util.UUID;

/**
 * Provides methods for converting between different types of Pilot objects.
 */
public interface PilotMapper {

  /**
   * Converts pilot response from a client call to spaceship DTO
   *
   * @param response pilot response from a client call to be converted from
   * @return pilot DTO
   */
  PilotDto clientResponseToDto(PilotClientResponse response);

  /**
   * Converts pilot DTO to pilot response
   *
   * @param dto pilot DTO to be converted from
   * @return pilot response
   */
  PilotResponse dtoToResponse(PilotDto dto);

  /**
   * Converts a list of pilot Ids to pilot request to a client
   *
   * @param ids list of pilot Ids to be converted from
   * @return pilot response to a client
   */
  PilotClientRequest idsToClientRequest(List<UUID> ids);
}
