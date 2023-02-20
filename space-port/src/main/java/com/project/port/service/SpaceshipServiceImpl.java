package com.project.port.service;

import com.project.port.client.PilotClient;
import com.project.port.client.SpaceshipClient;
import com.project.port.dto.SpaceshipDto;
import com.project.port.mapper.PilotMapper;
import com.project.port.mapper.SpaceshipMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class SpaceshipServiceImpl implements SpaceshipService {

  private final SpaceshipClient spaceshipClient;

  private final PilotClient pilotClient;

  private final SpaceshipMapper spaceshipMapper;

  private final PilotMapper pilotMapper;

  public SpaceshipServiceImpl(final SpaceshipClient spaceshipClient,
                              final SpaceshipMapper spaceshipMapper,
                              final PilotClient pilotClient,
                              final PilotMapper pilotMapper) {
    this.spaceshipClient = spaceshipClient;
    this.spaceshipMapper = spaceshipMapper;
    this.pilotClient = pilotClient;
    this.pilotMapper = pilotMapper;
  }

  @Override
  public List<SpaceshipDto> getAll() {

    final List<SpaceshipDto> spaceshipDtos = spaceshipClient.getSpaceships().stream()
        .map(spaceshipMapper::clientResponseToDto)
        .toList();
    log.info(spaceshipDtos.isEmpty() ? "No spaceships found" : "Spaceships found");

    return spaceshipDtos;
  }
}
