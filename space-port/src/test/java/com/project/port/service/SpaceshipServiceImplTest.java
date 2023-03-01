package com.project.port.service;

import com.project.port.client.PilotClientBase;
import com.project.port.client.SpaceshipClientBase;
import com.project.port.dto.PilotClientRequest;
import com.project.port.dto.PilotClientResponse;
import com.project.port.dto.PilotDto;
import com.project.port.dto.SpaceshipClientResponse;
import com.project.port.dto.SpaceshipDto;
import com.project.port.exception.NotFoundException;
import com.project.port.mapper.PilotMapper;
import com.project.port.mapper.SpaceshipMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.project.port.common.Constant.buildPilotClientRequest;
import static com.project.port.common.Constant.buildPilotClientResponse;
import static com.project.port.common.Constant.buildPilotDto;
import static com.project.port.common.Constant.buildSpaceshipClientResponse;
import static com.project.port.common.Constant.buildSpaceshipClientResponseWithNoCrewIds;
import static com.project.port.common.Constant.buildSpaceshipDto;
import static com.project.port.common.Constant.buildSpaceshipDtoWithNoCrewDetails;
import static com.project.port.common.Constant.buildSpaceshipDtoWithNoCrewDetailsAndCrewIds;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class SpaceshipServiceImplTest {

  @Mock
  private SpaceshipClientBase spaceshipClientMock;

  @Mock
  private SpaceshipMapper spaceshipMapperMock;

  @Mock
  private PilotClientBase pilotClientMock;

  @Mock
  private PilotMapper pilotMapperMock;

  private SpaceshipServiceImpl spaceshipService;

  private SpaceshipDto spaceshipDto;

  private SpaceshipDto spaceshipDtoWithNoCrewDetails;

  private SpaceshipDto spaceshipDtoWithNoCrewDetailsAndCrewIds;

  private SpaceshipClientResponse spaceshipClientResponse;

  private SpaceshipClientResponse spaceshipClientResponseWithNoCrewIds;

  private PilotClientRequest pilotClientRequest;

  private PilotClientResponse pilotClientResponse;

  private PilotDto pilotDto;

  @BeforeEach
  void setUp() {
    spaceshipService = new SpaceshipServiceImpl(spaceshipClientMock, pilotClientMock, spaceshipMapperMock, pilotMapperMock);
    spaceshipDto = buildSpaceshipDto();
    spaceshipDtoWithNoCrewDetails = buildSpaceshipDtoWithNoCrewDetails();
    spaceshipClientResponse = buildSpaceshipClientResponse();
    pilotClientRequest = buildPilotClientRequest();
    pilotClientResponse = buildPilotClientResponse();
    pilotDto = buildPilotDto();
    spaceshipDtoWithNoCrewDetailsAndCrewIds = buildSpaceshipDtoWithNoCrewDetailsAndCrewIds();
    spaceshipClientResponseWithNoCrewIds = buildSpaceshipClientResponseWithNoCrewIds();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(spaceshipClientMock, spaceshipMapperMock, pilotMapperMock, pilotClientMock);
  }

  @Test
  void getAll() {
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoCrewDetails);
    when(pilotMapperMock.idsToClientRequest(spaceshipDtoWithNoCrewDetails.getCrewIds())).thenReturn(pilotClientRequest);
    when(pilotClientMock.getPilotsByIds(pilotClientRequest)).thenReturn(List.of(pilotClientResponse));
    when(pilotMapperMock.clientResponseToDto(pilotClientResponse)).thenReturn(pilotDto);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .contains(spaceshipDto);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(spaceshipClientMock).getSpaceships();
    verify(pilotMapperMock).idsToClientRequest(spaceshipDto.getCrewIds());
    verify(pilotClientMock).getPilotsByIds(pilotClientRequest);
    verify(pilotMapperMock).clientResponseToDto(pilotClientResponse);
  }

  @Test
  void getAll_noSpaceshipsReturnedFromClient() {
    when(spaceshipClientMock.getSpaceships()).thenReturn(Collections.emptyList());

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual).isEmpty();
    verify(spaceshipClientMock).getSpaceships();
    verifyNoInteractions(spaceshipMapperMock);
    verifyNoInteractions(pilotMapperMock);
    verifyNoInteractions(pilotClientMock);
  }

  @Test
  void getAll_spaceshipsHaveNoPilots() {
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponseWithNoCrewIds));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponseWithNoCrewIds))
        .thenReturn(spaceshipDtoWithNoCrewDetailsAndCrewIds);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .contains(spaceshipDtoWithNoCrewDetailsAndCrewIds);
    assertThat(actual.get(0).getCrew()).isEmpty();
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponseWithNoCrewIds);
    verify(spaceshipClientMock).getSpaceships();
    verifyNoInteractions(pilotClientMock);
    verifyNoInteractions(pilotMapperMock);
  }

  @Test
  void getAll_noPilotsReturnedFromClient() {
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoCrewDetails);
    when(pilotMapperMock.idsToClientRequest(spaceshipDtoWithNoCrewDetails.getCrewIds())).thenReturn(pilotClientRequest);
    when(pilotClientMock.getPilotsByIds(pilotClientRequest)).thenReturn(Collections.emptyList());

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .contains(spaceshipDtoWithNoCrewDetails);
    assertThat(actual.get(0).getCrew()).isEmpty();
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(spaceshipClientMock).getSpaceships();
    verify(pilotMapperMock).idsToClientRequest(spaceshipDto.getCrewIds());
    verify(pilotClientMock).getPilotsByIds(pilotClientRequest);
    verify(pilotMapperMock, times(0)).clientResponseToDto(any());
  }

  @Test
  void getById() {
    final UUID reqId = spaceshipDtoWithNoCrewDetails.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoCrewDetails);
    when(pilotMapperMock.idsToClientRequest(spaceshipDtoWithNoCrewDetails.getCrewIds())).thenReturn(pilotClientRequest);
    when(pilotClientMock.getPilotsByIds(pilotClientRequest)).thenReturn(List.of(pilotClientResponse));
    when(pilotMapperMock.clientResponseToDto(pilotClientResponse)).thenReturn(pilotDto);

    final SpaceshipDto actual = spaceshipService.getById(reqId);

    assertThat(actual).isEqualTo(spaceshipDto);
    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(pilotMapperMock).idsToClientRequest(spaceshipDtoWithNoCrewDetails.getCrewIds());
    verify(pilotClientMock).getPilotsByIds(pilotClientRequest);
    verify(pilotMapperMock).clientResponseToDto(pilotClientResponse);
  }

  @Test
  void getById_notFound() {
    final UUID reqId = spaceshipDtoWithNoCrewDetails.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> spaceshipService.getById(reqId));

    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verifyNoInteractions(spaceshipMapperMock);
    verifyNoInteractions(pilotMapperMock);
    verifyNoInteractions(pilotClientMock);
  }

  @Test
  void getById_spaceshipHasNoPilots() {
    final UUID reqId = spaceshipDtoWithNoCrewDetailsAndCrewIds.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.ofNullable(spaceshipClientResponseWithNoCrewIds));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponseWithNoCrewIds))
        .thenReturn(spaceshipDtoWithNoCrewDetailsAndCrewIds);

    final SpaceshipDto actual = spaceshipService.getById(reqId);

    assertThat(actual).isEqualTo(spaceshipDtoWithNoCrewDetailsAndCrewIds);
    assertThat(actual.getCrew()).isEmpty();
    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponseWithNoCrewIds);
    verifyNoInteractions(pilotClientMock);
    verifyNoInteractions(pilotMapperMock);
  }

  @Test
  void getById_noPilotsReturnedFromClient() {
    final UUID reqId = spaceshipDtoWithNoCrewDetails.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoCrewDetails);
    when(pilotMapperMock.idsToClientRequest(spaceshipDtoWithNoCrewDetails.getCrewIds())).thenReturn(pilotClientRequest);
    when(pilotClientMock.getPilotsByIds(pilotClientRequest)).thenReturn(Collections.emptyList());

    final SpaceshipDto actual = spaceshipService.getById(reqId);

    assertThat(actual).isEqualTo(spaceshipDtoWithNoCrewDetails);
    assertThat(actual.getCrew()).isEmpty();
    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(pilotMapperMock).idsToClientRequest(spaceshipDto.getCrewIds());
    verify(pilotClientMock).getPilotsByIds(pilotClientRequest);
    verify(pilotMapperMock, times(0)).clientResponseToDto(any());
  }
}
