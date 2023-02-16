package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;

public interface SpaceshipMapper {

  SpaceshipResponse spaceshipDtoToResponse(SpaceshipDto spaceshipDto);

  SpaceshipDto spaceshipRequestToDto(SpaceshipRequest spaceshipRequest);

  SpaceshipDto spaceshipEntityToDto(SpaceshipEntity spaceshipEntity);

  SpaceshipEntity spaceshipDtoToEntity(SpaceshipDto spaceshipDto);

  void spaceshipEntityUpdateWithDto(SpaceshipEntity spaceshipEntity, SpaceshipDto spaceshipDto);
}
