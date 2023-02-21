package com.project.port.mapper;

import com.project.port.dto.SpaceshipClientResponse;
import com.project.port.dto.SpaceshipDto;
import com.project.port.dto.SpaceshipResponse;

public interface SpaceshipMapper {

  SpaceshipDto clientResponseToDto(SpaceshipClientResponse response);

  SpaceshipResponse dtoToResponse(SpaceshipDto dto);
}
