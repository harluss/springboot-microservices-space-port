package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;
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

  @Override
  public SpaceshipDto spaceshipEntityToDto(final SpaceshipEntity spaceshipEntity) {
    return SpaceshipDto.builder()
        .id(spaceshipEntity.getId())
        .name(spaceshipEntity.getName())
        .payload(spaceshipEntity.getPayload())
        .crew(spaceshipEntity.getCrew())
        .build();
  }
}
