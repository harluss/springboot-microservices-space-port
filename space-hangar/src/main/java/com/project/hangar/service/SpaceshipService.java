package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;

import java.util.List;
import java.util.UUID;

public interface SpaceshipService {

  List<SpaceshipDto> getSpaceships();

  SpaceshipDto getSpaceshipById(final UUID spaceshipId);

  SpaceshipDto addSpaceship(SpaceshipDto spaceshipDto);

  void deleteSpaceshipById(final UUID spaceshipId);
}
