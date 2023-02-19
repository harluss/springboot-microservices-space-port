package com.project.cantina.service;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.exception.NotFoundException;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.repository.PilotRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class PilotServiceImpl implements PilotService {

  public static final String NOT_FOUND_MESSAGE = "Spaceship not found";

  public static final String NOT_FOUND_ID_PROVIDED_MESSAGE = "Spaceship with id {} not found";

  private final PilotRepository pilotRepository;

  private final PilotMapper pilotMapper;

  public PilotServiceImpl(final PilotRepository pilotRepository, final PilotMapper pilotMapper) {
    this.pilotRepository = pilotRepository;
    this.pilotMapper = pilotMapper;
  }

  @Override
  public List<PilotDto> getAll() {

    final List<PilotDto> pilotDtos = pilotRepository.findAll().stream()
        .map(pilotMapper::entityToDto)
        .toList();
    log.info(pilotDtos.isEmpty() ? "No pilots found" : "Pilots found");

    return pilotDtos;
  }

  @Override
  public PilotDto getById(final UUID id) {

    final PilotDto pilotDto = pilotRepository.findById(id)
        .map(pilotMapper::entityToDto)
        .orElseThrow(() -> {
          log.info(NOT_FOUND_ID_PROVIDED_MESSAGE, id);
          throw new NotFoundException(NOT_FOUND_MESSAGE);
        });
    log.info("Spaceship with id {} found", id);

    return pilotDto;
  }
}
