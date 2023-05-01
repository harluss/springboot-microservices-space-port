package com.project.port.mapper.impl;

import com.project.port.dto.pilot.AddPilotClientRequest;
import com.project.port.dto.pilot.AddPilotRequest;
import com.project.port.dto.pilot.PilotClientResponse;
import com.project.port.dto.pilot.PilotDto;
import com.project.port.dto.pilot.PilotResponse;
import com.project.port.mapper.PilotMapper;
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

  @Override
  public PilotDto addRequestToDto(final AddPilotRequest addPilotRequest) {
    return PilotDto.builder()
        .name(addPilotRequest.getName())
        .species(addPilotRequest.getSpecies())
        .profession(addPilotRequest.getProfession())
        .weapons(addPilotRequest.getWeapons())
        .build();
  }

  @Override
  public AddPilotClientRequest dtoToClientAddRequest(final PilotDto dto) {
    return AddPilotClientRequest.builder()
        .name(dto.getName())
        .species(dto.getSpecies())
        .profession(dto.getProfession())
        .weapons(dto.getWeapons())
        .build();
  }
}
