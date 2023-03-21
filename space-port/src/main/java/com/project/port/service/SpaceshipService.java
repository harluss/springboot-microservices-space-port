package com.project.port.service;

import com.project.port.dto.spaceship.SpaceshipDto;
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

  /**
   * Adds new spaceship based on provided spaceship DTO
   *
   * @param spaceshipDto spaceship DTO
   * @return spaceship DTO of added spaceship with Id included
   */
  SpaceshipDto add(SpaceshipDto spaceshipDto);

  /**
   * Updates spaceship based on provided Id and spaceship DTO
   *
   * @param spaceshipDto spaceship DTO
   * @param spaceshipId  spaceship Id
   * @return spaceship DTO of updated spaceship
   * @throws NotFoundException when pilot with provided Id is not found
   */
  SpaceshipDto updateById(SpaceshipDto spaceshipDto, UUID spaceshipId);
}
