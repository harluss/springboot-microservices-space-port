package com.project.hangar.mapper;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;
import org.springframework.stereotype.Component;

@Component
public class SpaceshipMapperImpl implements SpaceshipMapper {

  @Override
  public SpaceshipResponse dtoToResponse(final SpaceshipDto spaceshipDto) {

    return SpaceshipResponse.builder()
        .id(spaceshipDto.getId())
        .name(spaceshipDto.getName())
        .classType(spaceshipDto.getClassType())
        .crew(spaceshipDto.getCrew())
        .payload(spaceshipDto.getPayload())
        .build();
  }

  @Override
  public SpaceshipDto requestToDto(final SpaceshipRequest spaceshipRequest) {

    return SpaceshipDto.builder()
        .name(spaceshipRequest.getName())
        .classType(spaceshipRequest.getClassType())
        .payload(spaceshipRequest.getPayload())
        .crew(spaceshipRequest.getCrew())
        .build();
  }

  @Override
  public SpaceshipDto entityToDto(final SpaceshipEntity spaceshipEntity) {

    return SpaceshipDto.builder()
        .id(spaceshipEntity.getId())
        .name(spaceshipEntity.getName())
        .classType(spaceshipEntity.getClassType())
        .payload(spaceshipEntity.getPayload())
        .crew(spaceshipEntity.getCrew())
        .build();
  }

  @Override
  public SpaceshipEntity dtoToEntity(final SpaceshipDto spaceshipDto) {

    return SpaceshipEntity.builder()
        .name(spaceshipDto.getName())
        .classType(spaceshipDto.getClassType())
        .payload(spaceshipDto.getPayload())
        .crew(spaceshipDto.getCrew())
        .build();
  }

  @Override
  public void updateEntityWithDto(final SpaceshipEntity spaceshipEntity, final SpaceshipDto spaceshipDto) {

    spaceshipEntity.setName(spaceshipDto.getName());
    spaceshipEntity.setClassType(spaceshipDto.getClassType());
    spaceshipEntity.setPayload(spaceshipDto.getPayload());
    spaceshipEntity.setCrew(spaceshipDto.getCrew());
  }
}
