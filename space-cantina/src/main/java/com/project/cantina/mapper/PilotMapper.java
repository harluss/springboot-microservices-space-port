package com.project.cantina.mapper;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.entity.PilotEntity;

public interface PilotMapper {

  PilotDto entityToDto(PilotEntity entity);

  PilotResponse dtoToResponse(PilotDto dto);
}
