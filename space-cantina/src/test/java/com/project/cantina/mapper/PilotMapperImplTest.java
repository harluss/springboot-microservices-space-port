package com.project.cantina.mapper;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotRequest;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.entity.PilotEntity;
import org.assertj.core.api.AssertionsForClassTypes;
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
  void dtoToEntity() {
    final PilotDto dto = buildDto().toBuilder()
        .id(null)
        .build();
    final PilotEntity expected = buildEntity().toBuilder()
        .id(null)
        .build();

    final PilotEntity actual = pilotMapper.dtoToEntity(dto);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToResponse() {
    final PilotDto dto = buildDto();
    final PilotResponse expected = buildResponse();

    final PilotResponse actual = pilotMapper.dtoToResponse(dto);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void requestToDto() {
    final PilotRequest request = buildRequest();
    final PilotDto expected = buildDto().toBuilder()
        .id(null)
        .build();

    final PilotDto actual = pilotMapper.requestToDto(request);

    AssertionsForClassTypes.assertThat(actual).isEqualTo(expected);
  }
}
