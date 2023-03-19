package com.project.port.mapper;

import com.project.port.dto.spaceship.AddSpaceshipClientRequest;
import com.project.port.dto.spaceship.AddSpaceshipRequest;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.SpaceshipResponse;

/**
 * Provides methods for converting between different types of Spaceship objects.
 */
public interface SpaceshipMapper {

  /**
   * Converts spaceship response from a client call to spaceship DTO
   *
   * @param response spaceship response from a client call to be converted from
   * @return spaceship DTO
   */
  SpaceshipDto clientResponseToDto(SpaceshipClientResponse response);

  /**
   * Converts spaceship DTO to spaceship response
   *
   * @param dto spaceship DTO to be converted from
   * @return spaceship response
   */
  SpaceshipResponse dtoToResponse(SpaceshipDto dto);

  /**
   * Converts spaceship request to spaceship DTO
   *
   * @param request spaceship request to be converted from
   * @return spaceship DTO
   */
  SpaceshipDto addRequestToDto(AddSpaceshipRequest request);

  /**
   * Convert spaceship DTO to spaceship client add request
   *
   * @param dto spaceship DTO to be converted from
   * @return spaceship client request
   */
  AddSpaceshipClientRequest dtoToClientAddRequest(SpaceshipDto dto);
}
