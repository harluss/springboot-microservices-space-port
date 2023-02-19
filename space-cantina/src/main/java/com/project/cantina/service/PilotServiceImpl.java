package com.project.cantina.service;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.repository.PilotRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PilotServiceImpl implements PilotService {

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
}
