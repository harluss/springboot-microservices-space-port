package com.project.cantina.mapper;

import com.project.cantina.dto.AddPilotRequest;
import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.entity.PilotEntity;
import com.project.cantina.mapper.impl.PilotMapperImpl;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.project.cantina.common.Constants.buildDto;
import static com.project.cantina.common.Constants.buildEntity;
import static com.project.cantina.common.Constants.buildRequest;
import static com.project.cantina.common.Constants.buildResponse;
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
    final AddPilotRequest request = buildRequest();
    final PilotDto expected = buildDto().toBuilder()
        .id(null)
        .build();

    final PilotDto actual = pilotMapper.addRequestToDto(request);

    AssertionsForClassTypes.assertThat(actual).isEqualTo(expected);
  }

  @Test
  void updateEntityWithDto() {
    final PilotEntity entity = buildEntity().toBuilder()
        .id(null)
        .build();
    final PilotDto updateDto = buildDto().toBuilder()
        .id(null)
        .species("Unknown")
        .profession("Jedi Master")
        .weapons(Collections.emptyList())
        .build();

    pilotMapper.updateEntityWithDto(entity, updateDto);

    AssertionsForClassTypes.assertThat(entity.getName()).isEqualTo(updateDto.getName());
    AssertionsForClassTypes.assertThat(entity.getSpecies()).isEqualTo(updateDto.getSpecies());
    AssertionsForClassTypes.assertThat(entity.getProfession()).isEqualTo(updateDto.getProfession());
    AssertionsForClassTypes.assertThat(entity.getWeapons()).isEqualTo(updateDto.getWeapons());
  }
}
