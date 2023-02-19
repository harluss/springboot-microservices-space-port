package com.project.hangar.mapper;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;

public interface SpaceshipMapper {

  SpaceshipResponse dtoToResponse(SpaceshipDto dto);

  SpaceshipDto requestToDto(SpaceshipRequest request);

  SpaceshipDto entityToDto(SpaceshipEntity entity);

  SpaceshipEntity dtoToEntity(SpaceshipDto dto);

  void updateEntityWithDto(SpaceshipEntity entity, SpaceshipDto dto);
}
