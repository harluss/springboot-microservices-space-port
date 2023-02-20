package com.project.port.mapper;

import com.project.port.dto.SpaceshipClientResponse;
import com.project.port.dto.SpaceshipDto;
import com.project.port.dto.SpaceshipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpaceshipMapperImpl implements SpaceshipMapper {

  private final PilotMapper pilotMapper;


  @Override
  public SpaceshipDto clientResponseToDto(final SpaceshipClientResponse response) {
    return SpaceshipDto.builder()
        .id(response.getId())
        .name(response.getName())
        .classType(response.getClassType())
        .maxSpeed(response.getMaxSpeed())
        .payload(response.getPayload())
        .crew(response.getCrew())
        .crewList(response.getCrewList())
        .armament(response.getArmament())
        .build();
  }

  @Override
  public SpaceshipResponse dtoToResponse(final SpaceshipDto dto) {
    return SpaceshipResponse.builder()
        .id(dto.getId())
        .name(dto.getName())
        .classType(dto.getClassType())
        .maxSpeed(dto.getMaxSpeed())
        .payload(dto.getPayload())
        .crew(dto.getCrew())
        .crewList(dto.getCrewListDetails().stream()
            .map(pilotMapper::dtoToResponse)
            .toList())
        .armament(dto.getArmament())
        .build();
  }
}
