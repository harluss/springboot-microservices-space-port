package com.project.cantina.mapper;

import com.project.cantina.dto.AddPilotRequest;
import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.dto.UpdatePilotRequest;
import com.project.cantina.entity.PilotEntity;

/**
 * Provides methods for converting between different types of Pilot objects.
 */
public interface PilotMapper {

  /**
   * Converts pilot entity to pilot DTO
   *
   * @param entity pilot entity to be converted from
   * @return pilot DTO
   */
  PilotDto entityToDto(PilotEntity entity);

  /**
   * Converts pilot DTO to pilot entity
   *
   * @param dto pilot DTO to be converted from
   * @return pilot entity
   */
  PilotEntity dtoToEntity(PilotDto dto);

  /**
   * Converts pilot DTO to pilot response
   *
   * @param dto pilot DTO to be converted from
   * @return pilot response
   */
  PilotResponse dtoToResponse(PilotDto dto);

  /**
   * Converts pilot request to pilot DTO
   *
   * @param request pilot request to be converted from
   * @return pilot DTO
   */
  PilotDto addRequestToDto(AddPilotRequest request);

  /**
   * Converts pilot update request to pilot DTO
   *
   * @param request pilot update request to be converted from
   * @return pilot DTO
   */
  PilotDto updateRequestToDto(UpdatePilotRequest request);

  /**
   * Updates pilot entity with values from pilot DTO
   *
   * @param entity pilot entity to be updated
   * @param dto    pilot DTO providing new values for the update
   */
  void updateEntityWithDto(PilotEntity entity, PilotDto dto);
}
