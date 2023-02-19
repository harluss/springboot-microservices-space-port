package com.project.cantina.mapper;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotRequest;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.entity.PilotEntity;

public interface PilotMapper {

  PilotDto entityToDto(PilotEntity entity);

  PilotEntity dtoToEntity(PilotDto dto);

  PilotResponse dtoToResponse(PilotDto dto);

  PilotDto requestToDto(PilotRequest request);
}
