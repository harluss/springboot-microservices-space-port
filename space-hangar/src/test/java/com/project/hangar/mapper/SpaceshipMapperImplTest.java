package com.project.hangar.mapper;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;
import com.project.hangar.mapper.impl.SpaceshipMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.project.hangar.common.Constants.buildDto;
import static com.project.hangar.common.Constants.buildEntity;
import static com.project.hangar.common.Constants.buildRequest;
import static com.project.hangar.common.Constants.buildResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SpaceshipMapperImplTest {

  private SpaceshipMapperImpl spaceshipMapper;

  @BeforeEach
  void setUp() {
    spaceshipMapper = new SpaceshipMapperImpl();
  }

  @Test
  void dtoToResponse() {
    final SpaceshipDto dto = buildDto();
    final SpaceshipResponse expected = buildResponse();

    final SpaceshipResponse actual = spaceshipMapper.dtoToResponse(dto);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void requestToDto() {
    final SpaceshipRequest request = buildRequest();
    final SpaceshipDto expected = buildDto().toBuilder()
        .id(null)
        .build();

    final SpaceshipDto actual = spaceshipMapper.requestToDto(request);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void entityToDto() {
    final SpaceshipEntity entity = buildEntity();
    final SpaceshipDto expected = buildDto();

    final SpaceshipDto actual = spaceshipMapper.entityToDto(entity);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToEntity() {
    final SpaceshipDto dto = buildDto().toBuilder()
        .id(null)
        .build();
    final SpaceshipEntity expected = buildEntity().toBuilder()
        .id(null)
        .build();

    final SpaceshipEntity actual = spaceshipMapper.dtoToEntity(dto);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void updateEntityWithDto() {
    final SpaceshipEntity entity = buildEntity().toBuilder()
        .id(null)
        .build();
    final SpaceshipDto updateDto = buildDto().toBuilder()
        .id(null)
        .maxSpeed(50)
        .payload(100)
        .build();

    spaceshipMapper.updateEntityWithDto(entity, updateDto);

    assertThat(entity.getName()).isEqualTo(updateDto.getName());
    assertThat(entity.getClassType()).isEqualTo(updateDto.getClassType());
    assertThat(entity.getMaxSpeed()).isEqualTo(updateDto.getMaxSpeed());
    assertThat(entity.getPayload()).isEqualTo(updateDto.getPayload());
  }
}
