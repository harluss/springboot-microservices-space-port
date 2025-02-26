package com.project.cantina.service;

import java.util.List;
import java.util.UUID;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.exception.NotFoundException;

/**
 * Provides methods for performing different operations on Pilot objects.
 */
public interface PilotService {

  /**
   * Returns list of all pilots
   *
   * @return list of pilot DTOs
   */
  List<PilotDto> getAll();

  /**
   * Returns pilot based on provided id
   *
   * @param pilotId pilot id
   * @return pilot DTO
   * @throws NotFoundException when pilot with provided id is not found
   */
  PilotDto getById(UUID pilotId);

  /**
   * Adds new pilot based on provided pilot DTO
   *
   * @param pilotDto pilot DTO
   * @return pilot DTO of added pilot with id included
   */
  PilotDto add(PilotDto pilotDto);

  /**
   * Updates pilot based on provided id and pilot DTO
   *
   * @param pilotDto pilot DTO
   * @param pilotId  pilot id
   * @return pilot DTO of updated pilot
   * @throws NotFoundException when pilot with provided id is not found
   */
  PilotDto updateById(PilotDto pilotDto, UUID pilotId);

  /**
   * Deletes pilot based on provided id
   *
   * @param pilotId pilot id
   * @throws NotFoundException when pilot with provided id is not found
   */
  void deleteById(final UUID pilotId);
}
