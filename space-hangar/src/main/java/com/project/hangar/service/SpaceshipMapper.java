package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;

public interface SpaceshipMapper {

  SpaceshipResponse dtoToResponse(SpaceshipDto spaceshipDto);

  SpaceshipDto requestToDto(SpaceshipRequest spaceshipRequest);

  SpaceshipDto entityToDto(SpaceshipEntity spaceshipEntity);

  SpaceshipEntity dtoToEntity(SpaceshipDto spaceshipDto);

  void updateEntityWithDto(SpaceshipEntity spaceshipEntity, SpaceshipDto spaceshipDto);
}
