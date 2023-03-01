package com.project.port.service;

import com.project.port.dto.SpaceshipDto;
import com.project.port.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Provides methods for performing different operations on Spaceship objects.
 */
public interface SpaceshipService {

  /**
   * Returns list of all spaceships
   *
   * @return list of spaceship DTOs
   */
  List<SpaceshipDto> getAll();

  /**
   * Returns spaceship by provided Id
   *
   * @param spaceshipId spaceship Id
   * @return spaceship DTO
   * @throws NotFoundException when spaceship with provided Id is not found
   */
  SpaceshipDto getById(final UUID spaceshipId);
}
