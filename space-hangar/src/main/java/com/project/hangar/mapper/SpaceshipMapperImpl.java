package com.project.hangar.mapper;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;
import org.springframework.stereotype.Component;

@Component
public class SpaceshipMapperImpl implements SpaceshipMapper {

  @Override
  public SpaceshipResponse dtoToResponse(final SpaceshipDto dto) {
    return SpaceshipResponse.builder()
        .id(dto.getId())
        .name(dto.getName())
        .classType(dto.getClassType())
        .crew(dto.getCrew())
        .payload(dto.getPayload())
        .build();
  }

  @Override
  public SpaceshipDto requestToDto(final SpaceshipRequest request) {
    return SpaceshipDto.builder()
        .name(request.getName())
        .classType(request.getClassType())
        .payload(request.getPayload())
        .crew(request.getCrew())
        .build();
  }

  @Override
  public SpaceshipDto entityToDto(final SpaceshipEntity entity) {
    return SpaceshipDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .classType(entity.getClassType())
        .payload(entity.getPayload())
        .crew(entity.getCrew())
        .build();
  }

  @Override
  public SpaceshipEntity dtoToEntity(final SpaceshipDto dto) {
    return SpaceshipEntity.builder()
        .name(dto.getName())
        .classType(dto.getClassType())
        .payload(dto.getPayload())
        .crew(dto.getCrew())
        .build();
  }

  @Override
  public void updateEntityWithDto(final SpaceshipEntity entity, final SpaceshipDto dto) {
    entity.setName(dto.getName());
    entity.setClassType(dto.getClassType());
    entity.setPayload(dto.getPayload());
    entity.setCrew(dto.getCrew());
  }
}
