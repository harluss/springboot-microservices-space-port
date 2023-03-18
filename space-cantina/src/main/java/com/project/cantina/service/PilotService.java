package com.project.cantina.service;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

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
   * Returns pilot based on provided Id
   *
   * @param pilotId pilot Id
   * @return pilot DTO
   * @throws NotFoundException when pilot with provided Id is not found
   */
  PilotDto getById(UUID pilotId);

  /**
   * Adds new pilot based on provided pilot DTO
   *
   * @param pilotDto pilot DTO
   * @return pilot DTO of added pilot with Id included
   */
  PilotDto add(PilotDto pilotDto);

  /**
   * Updates pilot based on provided Id and pilot DTO
   *
   * @param pilotDto pilot DTO
   * @param pilotId  pilot Id
   * @return pilot DTO of updated pilot
   * @throws NotFoundException when pilot with provided Id is not found
   */
  PilotDto updateById(PilotDto pilotDto, UUID pilotId);

  /**
   * Deletes pilot based on provided Id
   *
   * @param pilotId pilot Id
   * @throws NotFoundException when pilot with provided Id is not found
   */
  void deleteById(final UUID pilotId);
}
