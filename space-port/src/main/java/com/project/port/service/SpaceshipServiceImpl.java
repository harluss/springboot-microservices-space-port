package com.project.port.service;

import com.project.port.client.PilotClientBase;
import com.project.port.client.SpaceshipClientBase;
import com.project.port.dto.PilotDto;
import com.project.port.dto.SpaceshipDto;
import com.project.port.exception.NotFoundException;
import com.project.port.mapper.PilotMapper;
import com.project.port.mapper.SpaceshipMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class SpaceshipServiceImpl implements SpaceshipService {

  public static final String NOT_FOUND_MESSAGE = "Spaceship not found";

  public static final String NOT_FOUND_ID_PROVIDED_MESSAGE = "Spaceship with id {} not found";

  private final SpaceshipClientBase spaceshipClient;

  private final PilotClientBase pilotClient;

  private final SpaceshipMapper spaceshipMapper;

  private final PilotMapper pilotMapper;

  @Override
  public List<SpaceshipDto> getAll() {

    final List<SpaceshipDto> spaceshipDtos = spaceshipClient.getSpaceships().stream()
        .map(spaceshipMapper::clientResponseToDto)
        .toList();
    log.info("{} Spaceships found", spaceshipDtos.size());

    final List<UUID> pilotIds = getPilotIdsFromEachSpaceship(spaceshipDtos);

    if (spaceshipDtos.isEmpty() || pilotIds.isEmpty()) {
      return spaceshipDtos;
    }

    final List<PilotDto> pilotDtos = pilotClient.getPilotsByIds(pilotMapper.idsToClientRequest(pilotIds)).stream()
        .map(pilotMapper::clientResponseToDto)
        .toList();
    log.info("{} Pilots found", pilotDtos.size());

    spaceshipDtos.forEach(spaceship -> spaceship.setCrew(getSpaceshipCrewFromListOfPilots(spaceship.getCrewIds(), pilotDtos)));

    return spaceshipDtos;
  }

  @Override
  public SpaceshipDto getById(final UUID spaceshipId) {

    final SpaceshipDto spaceshipDto = spaceshipClient.getSpaceshipById(spaceshipId)
        .map(spaceshipMapper::clientResponseToDto)
        .orElseThrow(() -> {
          log.info(NOT_FOUND_ID_PROVIDED_MESSAGE, spaceshipId);
          throw new NotFoundException(NOT_FOUND_MESSAGE);
        });
    log.info("Spaceship with id {} found", spaceshipId);

    final List<UUID> pilotIds = spaceshipDto.getCrewIds();

    if (pilotIds.isEmpty()) {
      return spaceshipDto;
    }

    final List<PilotDto> pilotDtos = pilotClient.getPilotsByIds(pilotMapper.idsToClientRequest(pilotIds)).stream()
        .map(pilotMapper::clientResponseToDto)
        .toList();
    log.info("{} Pilots found", pilotDtos.size());

    spaceshipDto.setCrew(getSpaceshipCrewFromListOfPilots(spaceshipDto.getCrewIds(), pilotDtos));

    return spaceshipDto;
  }

  private List<UUID> getPilotIdsFromEachSpaceship(final List<SpaceshipDto> spaceshipDtos) {
    return spaceshipDtos.stream()
        .map(SpaceshipDto::getCrewIds)
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PilotDto> getSpaceshipCrewFromListOfPilots(final List<UUID> ids, final List<PilotDto> pilotDtos) {
    return pilotDtos.stream()
        .filter(pilot -> ids.contains(pilot.getId()))
        .toList();
  }
}
