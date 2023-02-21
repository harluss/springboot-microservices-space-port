package com.project.hangar.mapper;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;

public interface SpaceshipMapper {

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
  SpaceshipDto requestToDto(SpaceshipRequest request);

  /**
   * Converts spaceship entity to spaceship DTO
   *
   * @param entity spaceship entity to be converted from
   * @return spaceship DTO
   */
  SpaceshipDto entityToDto(SpaceshipEntity entity);

  /**
   * Converts spaceship DTO to spaceship entity
   *
   * @param dto spaceship DTO to be converted from
   * @return spaceship entity
   */
  SpaceshipEntity dtoToEntity(SpaceshipDto dto);

  /**
   * Updates spaceship entity with values from spaceship DTO
   *
   * @param entity spaceship entity to be updated
   * @param dto    spaceship DTO providing new values for the update
   */
  void updateEntityWithDto(SpaceshipEntity entity, SpaceshipDto dto);
}
