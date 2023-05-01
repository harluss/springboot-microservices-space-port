package com.project.port.mapper.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.project.port.dto.pilot.PilotDto;
import com.project.port.dto.pilot.PilotResponse;
import com.project.port.dto.spaceship.AddSpaceshipClientRequest;
import com.project.port.dto.spaceship.AddSpaceshipRequest;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.SpaceshipResponse;
import com.project.port.dto.spaceship.UpdateSpaceshipClientRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipResponse;
import com.project.port.mapper.PilotMapper;
import com.project.port.mapper.SpaceshipMapper;

@Component
@RequiredArgsConstructor
public class SpaceshipMapperImpl implements SpaceshipMapper {

  private final PilotMapper pilotMapper;

  @Override
  public SpaceshipDto clientResponseToDto(final SpaceshipClientResponse response) {
    return SpaceshipDto
        .builder()
        .id(response.getId())
        .name(response.getName())
        .classType(response.getClassType())
        .maxSpeed(response.getMaxSpeed())
        .payload(response.getPayload())
        .crewIds(response.getCrewIds())
        .armament(response.getArmament())
        .build();
  }

  @Override
  public SpaceshipResponse dtoToResponse(final SpaceshipDto dto) {
    final List<PilotResponse> pilotResponses = dto.getCrew().stream().map(pilotMapper::dtoToResponse).toList();

    return SpaceshipResponse
        .builder()
        .id(dto.getId())
        .name(dto.getName())
        .classType(dto.getClassType())
        .maxSpeed(dto.getMaxSpeed())
        .payload(dto.getPayload())
        .crew(pilotResponses)
        .armament(dto.getArmament())
        .build();
  }

  @Override
  public SpaceshipDto addRequestToDto(final AddSpaceshipRequest request) {
    final List<PilotDto> pilotDtos = request.getCrew().stream().map(pilotMapper::addRequestToDto).toList();

    return SpaceshipDto
        .builder()
        .name(request.getName())
        .classType(request.getClassType())
        .maxSpeed(request.getMaxSpeed())
        .payload(request.getPayload())
        .crew(pilotDtos)
        .armament(request.getArmament())
        .build();
  }

  @Override
  public AddSpaceshipClientRequest dtoToClientAddRequest(final SpaceshipDto dto) {
    return AddSpaceshipClientRequest
        .builder()
        .name(dto.getName())
        .classType(dto.getClassType())
        .maxSpeed(dto.getMaxSpeed())
        .payload(dto.getPayload())
        .crewIds(dto.getCrewIds())
        .armament(dto.getArmament())
        .build();
  }

  @Override
  public SpaceshipDto updateRequestToDto(final UpdateSpaceshipRequest request) {
    return SpaceshipDto
        .builder()
        .name(request.getName())
        .classType(request.getClassType())
        .maxSpeed(request.getMaxSpeed())
        .payload(request.getPayload())
        .crewIds(null)
        .crew(null)
        .armament(request.getArmament())
        .build();
  }

  @Override
  public UpdateSpaceshipClientRequest dtoToClientUpdateRequest(final SpaceshipDto dto) {
    return UpdateSpaceshipClientRequest
        .builder()
        .name(dto.getName())
        .classType(dto.getClassType())
        .maxSpeed(dto.getMaxSpeed())
        .payload(dto.getPayload())
        .crewIds(dto.getCrewIds())
        .armament(dto.getArmament())
        .build();
  }

  @Override
  public void updateDtoWithDto(final SpaceshipDto dtoToBeUpdated, final SpaceshipDto updateDto) {
    dtoToBeUpdated.setName(updateDto.getName());
    dtoToBeUpdated.setClassType(updateDto.getClassType());
    dtoToBeUpdated.setPayload(updateDto.getPayload());
    dtoToBeUpdated.setMaxSpeed(updateDto.getMaxSpeed());
    dtoToBeUpdated.setArmament(updateDto.getArmament());
  }

  @Override
  public UpdateSpaceshipResponse dtoToUpdateResponse(final SpaceshipDto dto) {
    return UpdateSpaceshipResponse
        .builder()
        .id(dto.getId())
        .name(dto.getName())
        .classType(dto.getClassType())
        .maxSpeed(dto.getMaxSpeed())
        .payload(dto.getPayload())
        .crewIds(dto.getCrewIds())
        .armament(dto.getArmament())
        .build();
  }
}
