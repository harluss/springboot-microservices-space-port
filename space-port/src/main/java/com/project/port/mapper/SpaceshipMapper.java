package com.project.port.mapper;

import com.project.port.dto.SpaceshipClientResponse;
import com.project.port.dto.SpaceshipDto;
import com.project.port.dto.SpaceshipResponse;

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
}
