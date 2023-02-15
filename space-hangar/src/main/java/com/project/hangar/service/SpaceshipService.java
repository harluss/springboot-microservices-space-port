package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;

import java.util.List;

public interface SpaceshipService {

  List<SpaceshipDto> getSpaceships();

  SpaceshipDto getSpaceshipById(final Integer spaceshipId);
}
