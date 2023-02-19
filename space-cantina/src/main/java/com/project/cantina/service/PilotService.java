package com.project.cantina.service;

import com.project.cantina.dto.PilotDto;

import java.util.List;
import java.util.UUID;

public interface PilotService {

  List<PilotDto> getAll();

  PilotDto getById(UUID pilotId);

  PilotDto add(PilotDto pilotDto);

  PilotDto updateById(PilotDto pilotDto, UUID pilotId);
}
