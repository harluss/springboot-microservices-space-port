package com.project.port.mapper;

import com.project.port.dto.PilotClientResponse;
import com.project.port.dto.PilotDto;
import com.project.port.dto.PilotResponse;
import org.springframework.stereotype.Component;

@Component
public class PilotMapperImpl implements PilotMapper {

  @Override
  public PilotDto clientResponseToDto(final PilotClientResponse response) {
    return PilotDto.builder()
        .id(response.getId())
        .name(response.getName())
        .species(response.getSpecies())
        .profession(response.getProfession())
        .weapons(response.getWeapons())
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
