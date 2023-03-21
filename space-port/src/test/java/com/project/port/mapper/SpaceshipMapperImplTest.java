package com.project.port.mapper;

import static com.project.port.common.Constant.buildAddSpaceshipClientRequest;
import static com.project.port.common.Constant.buildAddSpaceshipRequest;
import static com.project.port.common.Constant.buildPilotDto;
import static com.project.port.common.Constant.buildPilotResponse;
import static com.project.port.common.Constant.buildSpaceshipClientResponse;
import static com.project.port.common.Constant.buildSpaceshipDto;
import static com.project.port.common.Constant.buildSpaceshipDtoWithNoCrewDetails;
import static com.project.port.common.Constant.buildSpaceshipDtoWithNoCrewIds;
import static com.project.port.common.Constant.buildSpaceshipResponse;
import static com.project.port.common.Constant.buildUpdateSpaceshipClientRequest;
import static com.project.port.common.Constant.buildUpdateSpaceshipDto;
import static com.project.port.common.Constant.buildUpdateSpaceshipDtoWithNoCrewIds;
import static com.project.port.common.Constant.buildUpdateSpaceshipDtoWithNoId;
import static com.project.port.common.Constant.buildUpdateSpaceshipRequest;
import static com.project.port.common.Constant.buildUpdateSpaceshipResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.project.port.dto.pilot.PilotDto;
import com.project.port.dto.pilot.PilotResponse;
import com.project.port.dto.spaceship.AddSpaceshipClientRequest;
import com.project.port.dto.spaceship.AddSpaceshipRequest;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.SpaceshipResponse;
import com.project.port.dto.spaceship.UpdateSpaceshipClientRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipResponse;
import com.project.port.mapper.impl.SpaceshipMapperImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class SpaceshipMapperImplTest {

  @Mock
  private PilotMapper pilotMapperMock;

  private SpaceshipMapperImpl spaceshipMapper;

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(pilotMapperMock);
  }

  @BeforeEach
  void setUp() {
    spaceshipMapper = new SpaceshipMapperImpl(pilotMapperMock);
  }

  @Test
  void clientResponseToDto() {
    final SpaceshipClientResponse clientResponse = buildSpaceshipClientResponse();
    final SpaceshipDto expected = buildSpaceshipDto().toBuilder().crew(null).build();

    final SpaceshipDto actual = spaceshipMapper.clientResponseToDto(clientResponse);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToResponse() {
    final SpaceshipDto dto = buildSpaceshipDto();
    final SpaceshipResponse expected = buildSpaceshipResponse();
    final PilotResponse pilotResponse = buildPilotResponse();
    when(pilotMapperMock.dtoToResponse(dto.getCrew().get(0))).thenReturn(pilotResponse);

    final SpaceshipResponse actual = spaceshipMapper.dtoToResponse(dto);

    assertThat(actual).isEqualTo(expected);
    verify(pilotMapperMock).dtoToResponse(dto.getCrew().get(0));
  }

  @Test
  void addRequestToDto() {
    final AddSpaceshipRequest request = buildAddSpaceshipRequest();
    final SpaceshipDto expected = buildSpaceshipDtoWithNoCrewIds().toBuilder().id(null).build();
    final PilotDto pilotDto = buildPilotDto();
    when(pilotMapperMock.addRequestToDto(request.getCrew().get(0))).thenReturn(pilotDto);

    final SpaceshipDto actual = spaceshipMapper.addRequestToDto(request);

    assertThat(actual).isEqualTo(expected);
    verify(pilotMapperMock).addRequestToDto(request.getCrew().get(0));
  }

  @Test
  void dtoToClientAddRequest() {
    final SpaceshipDto dto = buildSpaceshipDtoWithNoCrewDetails().toBuilder().id(null).build();
    final AddSpaceshipClientRequest expected = buildAddSpaceshipClientRequest();

    final AddSpaceshipClientRequest actual = spaceshipMapper.dtoToClientAddRequest(dto);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void updateRequestToDto() {
    final UpdateSpaceshipRequest request = buildUpdateSpaceshipRequest();
    final SpaceshipDto expected = buildUpdateSpaceshipDtoWithNoCrewIds();

    final SpaceshipDto actual = spaceshipMapper.updateRequestToDto(request);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToClientUpdateRequest() {
    final SpaceshipDto dto = buildUpdateSpaceshipDtoWithNoId();
    final UpdateSpaceshipClientRequest expected = buildUpdateSpaceshipClientRequest();

    final UpdateSpaceshipClientRequest actual = spaceshipMapper.dtoToClientUpdateRequest(dto);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void updateEntityWithDto() {
    final SpaceshipDto dtoToBeUpdated = buildSpaceshipDto();
    final SpaceshipDto updateDto = buildUpdateSpaceshipDtoWithNoId();

    spaceshipMapper.updateDtoWithDto(dtoToBeUpdated, updateDto);

    assertThat(dtoToBeUpdated.getName()).isEqualTo(updateDto.getName());
    assertThat(dtoToBeUpdated.getClassType()).isEqualTo(updateDto.getClassType());
    assertThat(dtoToBeUpdated.getMaxSpeed()).isEqualTo(updateDto.getMaxSpeed());
    assertThat(dtoToBeUpdated.getPayload()).isEqualTo(updateDto.getPayload());
  }

  @Test
  void dtoToUpdateResponse() {
    final SpaceshipDto dto = buildUpdateSpaceshipDto();
    final UpdateSpaceshipResponse expected = buildUpdateSpaceshipResponse();

    final UpdateSpaceshipResponse actual = spaceshipMapper.dtoToUpdateResponse(dto);

    assertThat(actual).isEqualTo(expected);
  }
}
