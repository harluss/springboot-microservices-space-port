package com.project.port.service;

import com.project.port.dto.SpaceshipDto;

import java.util.List;

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
}
