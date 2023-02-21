package com.project.port.mapper;

import com.project.port.dto.PilotClientRequest;
import com.project.port.dto.PilotClientResponse;
import com.project.port.dto.PilotDto;
import com.project.port.dto.PilotResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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

  @Override
  public PilotClientRequest idsToClientRequest(final List<UUID> ids) {
    return PilotClientRequest.builder()
        .pilotIds(ids)
        .build();
  }
}
