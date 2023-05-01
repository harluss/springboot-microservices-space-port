package com.project.port.service;

import java.util.List;
import java.util.UUID;

import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.exception.NotFoundException;

/**
 * Provides methods for performing different operations on Spaceship objects.
 */
public interface SpaceshipService {

  /**
   * Returns list of all spaceships and their crews
   *
   * @return list of spaceship DTOs
   */
  List<SpaceshipDto> getAll();

  /**
   * Returns spaceship and its crew by provided id
   *
   * @param spaceshipId spaceship id
   * @return spaceship DTO
   * @throws NotFoundException when spaceship with provided id is not found
   */
  SpaceshipDto getById(final UUID spaceshipId);

  /**
   * Adds new spaceship and its crew based on provided spaceship DTO
   *
   * @param spaceshipDto spaceship DTO
   * @return spaceship DTO of added spaceship with id included
   */
  SpaceshipDto add(SpaceshipDto spaceshipDto);

  /**
   * Updates spaceship based on provided id and spaceship DTO
   *
   * @param spaceshipDto spaceship DTO
   * @param spaceshipId  spaceship id
   * @return spaceship DTO of updated spaceship
   * @throws NotFoundException when pilot with provided id is not found
   */
  SpaceshipDto updateById(SpaceshipDto spaceshipDto, UUID spaceshipId);

  /**
   * Deletes spaceship and its crew based on provided id
   *
   * @param spaceshipId spaceship id
   * @throws NotFoundException when pilot with provided id is not found
   */
  void deleteById(final UUID spaceshipId);
}
