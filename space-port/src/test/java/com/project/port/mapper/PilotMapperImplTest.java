package com.project.port.mapper;

import com.project.port.dto.PilotClientResponse;
import com.project.port.dto.PilotDto;
import com.project.port.dto.PilotResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.project.port.common.Constant.*;
import static org.assertj.core.api.Assertions.assertThat;

class PilotMapperImplTest {

  private PilotMapperImpl pilotMapper;

  @BeforeEach
  void setUp() {
    pilotMapper = new PilotMapperImpl();
  }

  @Test
  void clientResponseToDto() {
    final PilotClientResponse response = buildPilotClientResponse();
    final PilotDto expected = buildPilotDto();

    final PilotDto actual = pilotMapper.clientResponseToDto(response);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToResponse() {
    final PilotResponse expected = buildPilotResponse();
    final PilotDto dto = buildPilotDto();

    final PilotResponse actual = pilotMapper.dtoToResponse(dto);

    assertThat(actual).isEqualTo(expected);
  }
}
