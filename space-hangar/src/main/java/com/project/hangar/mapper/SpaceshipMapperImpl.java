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
        .maxSpeed(dto.getMaxSpeed())
        .crew(dto.getCrew())
        .crewList(dto.getCrewList())
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
        .crew(request.getCrew())
        .crewList(request.getCrewList())
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
        .crew(entity.getCrew())
        .crewList(entity.getCrewList())
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
        .crew(dto.getCrew())
        .crewList(dto.getCrewList())
        .armament(dto.getArmament())
        .build();
  }

  @Override
  public void updateEntityWithDto(final SpaceshipEntity entity, final SpaceshipDto dto) {
    entity.setName(dto.getName());
    entity.setClassType(dto.getClassType());
    entity.setPayload(dto.getPayload());
    entity.setMaxSpeed(dto.getMaxSpeed());
    entity.setCrew(dto.getCrew());
    entity.setCrewList(dto.getCrewList());
    entity.setArmament(dto.getArmament());
  }
}
