package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipResponse;

public interface SpaceshipMapper {

  SpaceshipResponse spaceshipDtoToResponse(SpaceshipDto spaceshipDto);
}
