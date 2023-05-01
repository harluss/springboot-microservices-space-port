package com.project.port.mapper;

import com.project.port.dto.spaceship.AddSpaceshipClientRequest;
import com.project.port.dto.spaceship.AddSpaceshipRequest;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.SpaceshipResponse;
import com.project.port.dto.spaceship.UpdateSpaceshipClientRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipResponse;

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
   * Converts add spaceship request to spaceship DTO
   *
   * @param request add spaceship request to be converted from
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

  /**
   * Converts update spaceship request to spaceship DTO
   *
   * @param request update spaceship request to be converted from
   * @return spaceship DTO
   */
  SpaceshipDto updateRequestToDto(UpdateSpaceshipRequest request);

  /**
   * Converts spaceship DTO into update spaceship client request
   *
   * @param dto spaceship DTO to be converted from
   * @return update spaceship client request
   */
  UpdateSpaceshipClientRequest dtoToClientUpdateRequest(SpaceshipDto dto);

  /**
   * Updates spaceship DTO with values from update spaceship DTO
   *
   * @param dtoToBeUpdated spaceship DTO to be updated
   * @param updateDto      spaceship DTO providing new values for the update
   */
  void updateDtoWithDto(SpaceshipDto dtoToBeUpdated, SpaceshipDto updateDto);

  /**
   * Converts spaceship DTO to update spaceship response
   *
   * @param dto spaceship DTO to be converted from
   * @return update spaceship response
   */
  UpdateSpaceshipResponse dtoToUpdateResponse(SpaceshipDto dto);
}
