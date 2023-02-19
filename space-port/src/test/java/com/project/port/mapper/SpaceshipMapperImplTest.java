package com.project.port.mapper;

import com.project.port.dto.SpaceshipClientResponse;
import com.project.port.dto.SpaceshipDto;
import com.project.port.dto.SpaceshipResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.project.port.common.Constant.*;
import static org.assertj.core.api.Assertions.assertThat;

class SpaceshipMapperImplTest {

  private SpaceshipMapperImpl spaceshipMapper;

  @BeforeEach
  void setUp() {
    spaceshipMapper = new SpaceshipMapperImpl();
  }

  @Test
  void clientResponseToDto() {
    final SpaceshipClientResponse clientResponse = buildClientResponse();
    final SpaceshipDto expected = buildDto();

    final SpaceshipDto actual = spaceshipMapper.clientResponseToDto(clientResponse);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToResponse() {
    final SpaceshipDto dto = buildDto();
    final SpaceshipResponse expected = buildResponse();

    final SpaceshipResponse actual = spaceshipMapper.dtoToResponse(dto);

    assertThat(actual).isEqualTo(expected);
  }
}
