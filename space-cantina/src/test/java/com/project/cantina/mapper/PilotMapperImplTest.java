package com.project.cantina.mapper;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.entity.PilotEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.project.cantina.common.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

class PilotMapperImplTest {

  private PilotMapperImpl pilotMapper;

  @BeforeEach
  void setUp() {
    pilotMapper = new PilotMapperImpl();
  }

  @Test
  void entityToDto() {
    final PilotEntity entity = buildEntity();
    final PilotDto expected = buildDto();

    final PilotDto actual = pilotMapper.entityToDto(entity);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToResponse() {
    final PilotDto dto = buildDto();
    final PilotResponse expected = buildResponse();

    final PilotResponse actual = pilotMapper.dtoToResponse(dto);

    assertThat(actual).isEqualTo(expected);
  }
}
