package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;

import java.util.List;
import java.util.UUID;

public interface SpaceshipService {

  List<SpaceshipDto> getAll();

  SpaceshipDto getById(final UUID spaceshipId);

  SpaceshipDto add(SpaceshipDto spaceshipDto);

  SpaceshipDto updateById(SpaceshipDto spaceshipDto, UUID spaceshipId);

  void deleteById(final UUID spaceshipId);
}
