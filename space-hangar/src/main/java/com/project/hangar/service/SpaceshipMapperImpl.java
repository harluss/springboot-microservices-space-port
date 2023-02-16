package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
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
        .classType(spaceshipDto.getClassType())
        .crew(spaceshipDto.getCrew())
        .payload(spaceshipDto.getPayload())
        .build();
  }

  @Override
  public SpaceshipDto spaceshipRequestToDto(final SpaceshipRequest spaceshipRequest) {
    return SpaceshipDto.builder()
        .name(spaceshipRequest.getName())
        .classType(spaceshipRequest.getClassType())
        .payload(spaceshipRequest.getPayload())
        .crew(spaceshipRequest.getCrew())
        .build();
  }

  @Override
  public SpaceshipDto spaceshipEntityToDto(final SpaceshipEntity spaceshipEntity) {
    return SpaceshipDto.builder()
        .id(spaceshipEntity.getId())
        .name(spaceshipEntity.getName())
        .classType(spaceshipEntity.getClassType())
        .payload(spaceshipEntity.getPayload())
        .crew(spaceshipEntity.getCrew())
        .build();
  }

  @Override
  public SpaceshipEntity spaceshipDtoToEntity(final SpaceshipDto spaceshipDto) {
    return SpaceshipEntity.builder()
        .name(spaceshipDto.getName())
        .classType(spaceshipDto.getClassType())
        .payload(spaceshipDto.getPayload())
        .crew(spaceshipDto.getCrew())
        .build();
  }
}
