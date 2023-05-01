package com.project.port.mapper;

import com.project.port.dto.pilot.AddPilotClientRequest;
import com.project.port.dto.pilot.AddPilotRequest;
import com.project.port.dto.pilot.PilotClientResponse;
import com.project.port.dto.pilot.PilotDto;
import com.project.port.dto.pilot.PilotResponse;
import com.project.port.mapper.impl.PilotMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.project.port.common.Constant.buildAddPilotClientRequest;
import static com.project.port.common.Constant.buildAddPilotDto;
import static com.project.port.common.Constant.buildAddPilotRequest;
import static com.project.port.common.Constant.buildPilotClientResponse;
import static com.project.port.common.Constant.buildPilotDto;
import static com.project.port.common.Constant.buildPilotResponse;
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

  @Test
  void addRequestToDto() {
    final AddPilotRequest request = buildAddPilotRequest();
    final PilotDto expected = buildAddPilotDto();

    final PilotDto actual = pilotMapper.addRequestToDto(request);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToClientAddRequest() {
    final PilotDto dto = buildAddPilotDto();
    final AddPilotClientRequest expected = buildAddPilotClientRequest();

    final AddPilotClientRequest actual = pilotMapper.dtoToClientAddRequest(dto);

    assertThat(actual).isEqualTo(expected);
  }
}
