package com.project.port.service;

import com.project.port.client.PilotClient;
import com.project.port.client.SpaceshipClient;
import com.project.port.dto.PilotDto;
import com.project.port.dto.SpaceshipDto;
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

  private final SpaceshipClient spaceshipClient;

  private final PilotClient pilotClient;

  private final SpaceshipMapper spaceshipMapper;

  private final PilotMapper pilotMapper;

  @Override
  public List<SpaceshipDto> getAll() {

    final List<SpaceshipDto> spaceshipDtos = spaceshipClient.getSpaceships().stream()
        .map(spaceshipMapper::clientResponseToDto)
        .toList();
    log.info(spaceshipDtos.isEmpty() ? "No spaceshipDtos found" : "Spaceships found");

    final List<UUID> pilotIds = getPilotIds(spaceshipDtos);

    final List<PilotDto> pilotDtos = pilotClient.getPilotsByIds(pilotMapper.idsToClientRequest(pilotIds)).stream()
        .map(pilotMapper::clientResponseToDto)
        .toList();

    spaceshipDtos.forEach(spaceship -> spaceship.setCrew(getCrew(spaceship.getCrewIds(), pilotDtos)));

    return spaceshipDtos;
  }

  private List<UUID> getPilotIds(final List<SpaceshipDto> spaceshipDtos) {
    return spaceshipDtos.stream()
        .map(SpaceshipDto::getCrewIds)
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PilotDto> getCrew(final List<UUID> ids, final List<PilotDto> pilotDtos) {
    return pilotDtos.stream()
        .filter(pilot -> ids.contains(pilot.getId()))
        .toList();
  }
}
