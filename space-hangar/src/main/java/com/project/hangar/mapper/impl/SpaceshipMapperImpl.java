package com.project.hangar.mapper.impl;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;
import com.project.hangar.mapper.SpaceshipMapper;
import org.springframework.stereotype.Component;

@Component
public class SpaceshipMapperImpl implements SpaceshipMapper {

  @Override
  public SpaceshipResponse dtoToResponse(final SpaceshipDto dto) {
    return SpaceshipResponse.builder()
        .id(dto.getId())
        .name(dto.getName())
        .classType(dto.getClassType())
        .maxSpeed(dto.getMaxSpeed())
        .crewIds(dto.getCrewIds())
        .payload(dto.getPayload())
        .armament(dto.getArmament())
        .build();
  }

  @Override
  public SpaceshipDto requestToDto(final SpaceshipRequest request) {
    return SpaceshipDto.builder()
        .name(request.getName())
        .classType(request.getClassType())
        .maxSpeed(request.getMaxSpeed())
        .payload(request.getPayload())
        .crewIds(request.getCrewIds())
        .armament(request.getArmament())
        .build();
  }

  @Override
  public SpaceshipDto entityToDto(final SpaceshipEntity entity) {
    return SpaceshipDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .classType(entity.getClassType())
        .payload(entity.getPayload())
        .maxSpeed(entity.getMaxSpeed())
        .crewIds(entity.getCrewIds())
        .armament(entity.getArmament())
        .build();
  }

  @Override
  public SpaceshipEntity dtoToEntity(final SpaceshipDto dto) {
    return SpaceshipEntity.builder()
        .name(dto.getName())
        .classType(dto.getClassType())
        .payload(dto.getPayload())
        .maxSpeed(dto.getMaxSpeed())
        .crewIds(dto.getCrewIds())
        .armament(dto.getArmament())
        .build();
  }

  @Override
  public void updateEntityWithDto(final SpaceshipEntity entity, final SpaceshipDto dto) {
    entity.setName(dto.getName());
    entity.setClassType(dto.getClassType());
    entity.setPayload(dto.getPayload());
    entity.setMaxSpeed(dto.getMaxSpeed());
    entity.setCrewIds(dto.getCrewIds());
    entity.setArmament(dto.getArmament());
  }
}
