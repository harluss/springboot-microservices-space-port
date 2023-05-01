package com.project.hangar.service;

import java.util.List;
import java.util.UUID;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.exception.NotFoundException;

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
   * Returns spaceship based on provided id
   *
   * @param spaceshipId spaceship id
   * @return spaceship DTO
   * @throws NotFoundException when spaceship with provided id is not found
   */
  SpaceshipDto getById(final UUID spaceshipId);

  /**
   * Adds new spaceship based on provided spaceship DTO
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
   * Deletes spaceship based on provided id
   *
   * @param spaceshipId spaceship id
   * @throws NotFoundException when spaceship with provided id is not found
   */
  void deleteById(final UUID spaceshipId);
}
