package com.project.cantina.mapper.impl;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotRequest;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.dto.PilotUpdateRequest;
import com.project.cantina.entity.PilotEntity;
import com.project.cantina.mapper.PilotMapper;
import org.springframework.stereotype.Component;

@Component
public class PilotMapperImpl implements PilotMapper {

  @Override
  public PilotDto entityToDto(final PilotEntity entity) {
    return PilotDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .species(entity.getSpecies())
        .profession(entity.getProfession())
        .weapons(entity.getWeapons())
        .build();
  }

  @Override
  public PilotEntity dtoToEntity(final PilotDto dto) {
    return PilotEntity.builder()
        .name(dto.getName())
        .species(dto.getSpecies())
        .profession(dto.getProfession())
        .weapons(dto.getWeapons())
        .build();
  }

  @Override
  public PilotResponse dtoToResponse(final PilotDto dto) {
    return PilotResponse.builder()
        .id(dto.getId())
        .name(dto.getName())
        .species(dto.getSpecies())
        .profession(dto.getProfession())
        .weapons(dto.getWeapons())
        .build();
  }

  @Override
  public PilotDto requestToDto(final PilotRequest request) {
    return PilotDto.builder()
        .name(request.getName())
        .species(request.getSpecies())
        .profession(request.getProfession())
        .weapons(request.getWeapons())
        .build();
  }

  @Override
  public PilotDto updateRequestToDto(final PilotUpdateRequest request) {
    return PilotDto.builder()
        .species(request.getSpecies())
        .profession(request.getProfession())
        .weapons(request.getWeapons())
        .build();
  }

  @Override
  public void updateEntityWithDto(final PilotEntity entity, final PilotDto dto) {
    entity.setSpecies(dto.getSpecies());
    entity.setProfession(dto.getProfession());
    entity.setWeapons(dto.getWeapons());
  }
}
