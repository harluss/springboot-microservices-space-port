package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;

public interface SpaceshipMapper {

  SpaceshipResponse spaceshipDtoToResponse(SpaceshipDto spaceshipDto);

  SpaceshipDto spaceshipEntityToDto(SpaceshipEntity spaceshipEntity);
}
