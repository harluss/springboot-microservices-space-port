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
@RequiredArgsConstructor
@Service
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

    final List<UUID> pilotIds = spaceshipDtos.stream()
        .map(SpaceshipDto::getCrewList)
        .flatMap(Collection::stream)
        .toList();

    final List<PilotDto> pilotDtos = pilotClient.getPilotsByIds(pilotMapper.uuidsToClientRequest(pilotIds)).stream()
        .map(pilotMapper::clientResponseToDto)
        .toList();

    spaceshipDtos.forEach(s -> s.setCrewListDetails(getCrew(s.getCrewList(), pilotDtos)));

    return spaceshipDtos;
  }

  private List<PilotDto> getCrew(final List<UUID> ids, final List<PilotDto> pilots) {
    return pilots.stream().filter(p -> ids.contains(p.getId())).toList();
  }
}
