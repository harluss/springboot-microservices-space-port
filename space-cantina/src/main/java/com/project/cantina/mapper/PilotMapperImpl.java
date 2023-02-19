package com.project.cantina.mapper;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.entity.PilotEntity;
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
  public PilotResponse dtoToResponse(final PilotDto dto) {
    return PilotResponse.builder()
        .id(dto.getId())
        .name(dto.getName())
        .species(dto.getSpecies())
        .profession(dto.getProfession())
        .weapons(dto.getWeapons())
        .build();
  }
}
