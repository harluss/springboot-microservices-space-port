package com.project.hangar.mapper;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SpaceshipMapperImplTest {

  private static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

  private static final String NAME = "Tie Fighter";

  private static final String CLASS_TYPE = "Starfighter";

  private static final int CREW = 1;

  private static final int PAYLOAD = 10;

  private SpaceshipMapperImpl spaceshipMapper;

  @BeforeEach
  void setUp() {
    spaceshipMapper = new SpaceshipMapperImpl();
  }

  @Test
  void dtoToResponse() {
    final SpaceshipDto dto = buildDtoNoId().toBuilder()
        .id(ID)
        .build();
    final SpaceshipResponse expected = buildResponse();

    final SpaceshipResponse actual = spaceshipMapper.dtoToResponse(dto);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void requestToDto() {
    final SpaceshipRequest request = buildRequest();
    final SpaceshipDto expected = buildDtoNoId();

    final SpaceshipDto actual = spaceshipMapper.requestToDto(request);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void entityToDto() {
    final SpaceshipEntity entity = buildEntityNoId().toBuilder()
        .id(ID)
        .build();
    final SpaceshipDto expected = buildDtoNoId().toBuilder()
        .id(ID)
        .build();

    final SpaceshipDto actual = spaceshipMapper.entityToDto(entity);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToEntity() {
    final SpaceshipDto dto = buildDtoNoId();
    final SpaceshipEntity expected = buildEntityNoId();

    final SpaceshipEntity actual = spaceshipMapper.dtoToEntity(dto);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void updateEntityWithDto() {
    final SpaceshipEntity entity = buildEntityNoId();
    final SpaceshipDto updateDto = buildDtoNoId().toBuilder()
        .crew(5)
        .payload(100)
        .build();

    spaceshipMapper.updateEntityWithDto(entity, updateDto);

    assertThat(entity.getName()).isEqualTo(updateDto.getName());
    assertThat(entity.getClassType()).isEqualTo(updateDto.getClassType());
    assertThat(entity.getCrew()).isEqualTo(updateDto.getCrew());
    assertThat(entity.getPayload()).isEqualTo(updateDto.getPayload());
  }

  private SpaceshipDto buildDtoNoId() {
    return SpaceshipDto.builder()
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .build();
  }

  private SpaceshipResponse buildResponse() {
    return SpaceshipResponse.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .build();
  }

  private SpaceshipEntity buildEntityNoId() {
    return SpaceshipEntity.builder()
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .build();
  }

  private SpaceshipRequest buildRequest() {
    return SpaceshipRequest.builder()
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .build();
  }
}
