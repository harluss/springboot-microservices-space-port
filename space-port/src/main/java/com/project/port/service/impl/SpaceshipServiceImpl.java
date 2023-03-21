package com.project.port.service.impl;

import static java.util.Objects.isNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import com.project.port.client.PilotClient;
import com.project.port.client.SpaceshipClient;
import com.project.port.dto.pilot.PilotClientResponse;
import com.project.port.dto.pilot.PilotDto;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.UpdateSpaceshipClientRequest;
import com.project.port.exception.AddPilotException;
import com.project.port.exception.NotFoundException;
import com.project.port.exception.PilotExistsException;
import com.project.port.mapper.PilotMapper;
import com.project.port.mapper.SpaceshipMapper;
import com.project.port.service.SpaceshipService;

@Log4j2
@Service
@RequiredArgsConstructor
public class SpaceshipServiceImpl implements SpaceshipService {

  private static final String NOT_FOUND_MESSAGE = "Spaceship not found";

  private static final String NOT_FOUND_ID_PROVIDED_MESSAGE = "Spaceship with id {} not found";

  private static final String PILOTS_EXIST_ERROR_MESSAGE = "Pilots already exist ";

  private static final String PILOTS_ADD_ERROR_MESSAGE = "Failed to add pilots, try again later";

  private final SpaceshipClient spaceshipClient;

  private final PilotClient pilotClient;

  private final SpaceshipMapper spaceshipMapper;

  private final PilotMapper pilotMapper;

  @Override
  public List<SpaceshipDto> getAll() {

    final List<SpaceshipDto> spaceshipDtos = spaceshipClient
        .getSpaceships()
        .stream()
        .map(spaceshipMapper::clientResponseToDto)
        .toList();
    log.info("{} Spaceships found", spaceshipDtos.size());

    final List<UUID> pilotIds = getPilotIdsFromEachSpaceship(spaceshipDtos);

    if (spaceshipDtos.isEmpty() || pilotIds.isEmpty()) {
      return spaceshipDtos;
    }

    final List<PilotDto> pilotDtos = pilotClient
        .getPilots()
        .stream()
        .filter(p -> pilotIds.contains(p.getId()))
        .map(pilotMapper::clientResponseToDto)
        .toList();
    log.info("{} Pilots found", pilotDtos.size());

    spaceshipDtos.forEach(s -> s.setCrew(getSpaceshipCrewFromListOfPilots(s.getCrewIds(), pilotDtos)));

    return spaceshipDtos;
  }

  @Override
  public SpaceshipDto getById(final UUID spaceshipId) {

    final SpaceshipDto spaceshipDto = spaceshipClient
        .getSpaceshipById(spaceshipId)
        .map(spaceshipMapper::clientResponseToDto)
        .orElseThrow(() -> {
          log.info(NOT_FOUND_ID_PROVIDED_MESSAGE, spaceshipId);
          throw new NotFoundException(NOT_FOUND_MESSAGE);
        });
    log.info("Spaceship with id {} found", spaceshipId);

    final List<UUID> pilotIds = spaceshipDto.getCrewIds();

    if (isNull(pilotIds) || pilotIds.isEmpty()) {
      return spaceshipDto;
    }

    final List<PilotDto> pilotDtos = pilotClient
        .getPilots()
        .stream()
        .filter(p -> pilotIds.contains(p.getId()))
        .map(pilotMapper::clientResponseToDto)
        .toList();
    log.info("{} Pilots found", pilotDtos.size());

    spaceshipDto.setCrew(pilotDtos);

    return spaceshipDto;
  }

  @Override
  public SpaceshipDto add(final SpaceshipDto spaceshipDto) {

    final List<String> pilotNames = spaceshipDto.getCrew().stream().map(PilotDto::getName).toList();

    final List<String> existingNames = pilotClient
        .getPilots()
        .stream()
        .map(PilotClientResponse::getName)
        .filter(pilotNames::contains)
        .toList();

    if (!existingNames.isEmpty()) {
      log.info(PILOTS_EXIST_ERROR_MESSAGE + existingNames);
      throw new PilotExistsException(PILOTS_EXIST_ERROR_MESSAGE + existingNames);
    }

    final List<PilotDto> savedPilotDtos = spaceshipDto
        .getCrew()
        .stream()
        .map(pilotMapper::dtoToClientAddRequest)
        .map(pilotClient::addPilot)
        .filter(Objects::nonNull)
        .map(pilotMapper::clientResponseToDto)
        .toList();

    if (savedPilotDtos.isEmpty()) {
      log.info(PILOTS_ADD_ERROR_MESSAGE);
      throw new AddPilotException(PILOTS_ADD_ERROR_MESSAGE);
    }

    final List<UUID> savedPilotIds = savedPilotDtos.stream().map(PilotDto::getId).toList();

    spaceshipDto.setCrewIds(savedPilotIds);

    final SpaceshipClientResponse savedSpaceship = spaceshipClient.addSpaceship(
        spaceshipMapper.dtoToClientAddRequest(spaceshipDto));
    final SpaceshipDto savedSpaceshipDto = spaceshipMapper.clientResponseToDto(savedSpaceship);

    savedSpaceshipDto.setCrew(savedPilotDtos);

    return savedSpaceshipDto;
  }

  @Override
  public SpaceshipDto updateById(final SpaceshipDto updateSpaceshipDto, final UUID updateSpaceshipId) {

    final SpaceshipDto spaceshipToBeUpdated = spaceshipClient
        .getSpaceshipById(updateSpaceshipId)
        .map(spaceshipMapper::clientResponseToDto)
        .orElseThrow(() -> {
          log.info(NOT_FOUND_ID_PROVIDED_MESSAGE, updateSpaceshipId);
          throw new NotFoundException(NOT_FOUND_MESSAGE);
        });

    spaceshipMapper.updateDtoWithDto(spaceshipToBeUpdated, updateSpaceshipDto);
    final UpdateSpaceshipClientRequest updateSpaceshipClientRequest = spaceshipMapper.dtoToClientUpdateRequest(
        spaceshipToBeUpdated);
    final SpaceshipClientResponse updatedSpaceshipClientResponse = spaceshipClient.updateSpaceshipById(
        updateSpaceshipId, updateSpaceshipClientRequest);
    final SpaceshipDto updatedSpaceshipDto = spaceshipMapper.clientResponseToDto(updatedSpaceshipClientResponse);
    log.info("Spaceship with id {} updated: {}", updatedSpaceshipDto.getId(), updatedSpaceshipDto);

    return updatedSpaceshipDto;
  }

  private List<UUID> getPilotIdsFromEachSpaceship(final List<SpaceshipDto> spaceshipDtos) {
    return spaceshipDtos
        .stream()
        .map(SpaceshipDto::getCrewIds)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PilotDto> getSpaceshipCrewFromListOfPilots(final List<UUID> ids, final List<PilotDto> pilotDtos) {
    return pilotDtos.stream().filter(pilot -> ids.contains(pilot.getId())).toList();
  }
}
