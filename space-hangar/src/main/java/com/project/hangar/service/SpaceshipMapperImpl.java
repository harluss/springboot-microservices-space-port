package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipResponse;
import org.springframework.stereotype.Service;

@Service
public class SpaceshipMapperImpl implements SpaceshipMapper {

  @Override
  public SpaceshipResponse spaceshipDtoToResponse(final SpaceshipDto spaceshipDto) {
    return SpaceshipResponse.builder()
        .id(spaceshipDto.getId())
        .name(spaceshipDto.getName())
        .crew(spaceshipDto.getCrew())
        .payload(spaceshipDto.getPayload())
        .build();
  }
}
