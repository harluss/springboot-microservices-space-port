package com.project.port.service;

import com.project.port.client.PilotClient;
import com.project.port.client.SpaceshipClient;
import com.project.port.dto.*;
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

import static com.project.port.common.Constant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class SpaceshipServiceImplTest {

  @Mock
  private SpaceshipClient spaceshipClientMock;

  @Mock
  private SpaceshipMapper spaceshipMapperMock;

  @Mock
  private PilotClient pilotClientMock;

  @Mock
  private PilotMapper pilotMapperMock;

  private SpaceshipServiceImpl spaceshipService;

  private SpaceshipDto spaceshipDto;

  private SpaceshipClientResponse spaceshipClientResponse;

  private PilotClientRequest pilotClientRequest;

  private PilotClientResponse pilotClientResponse;

  private PilotDto pilotDto;

  @BeforeEach
  void setUp() {
    spaceshipService = new SpaceshipServiceImpl(spaceshipClientMock, pilotClientMock, spaceshipMapperMock, pilotMapperMock);
    spaceshipDto = buildSpaceshipDto();
    spaceshipClientResponse = buildSpaceshipClientResponse();
    pilotClientRequest = buildPilotClientRequest();
    pilotClientResponse = buildPilotClientResponse();
    pilotDto = buildPilotDto();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(spaceshipClientMock, spaceshipMapperMock, pilotMapperMock, pilotClientMock);
  }

  @Test
  void getAll() {
    final SpaceshipDto spaceshipDtoWithNoPilotDetails = spaceshipDto.toBuilder()
        .crew(Collections.emptyList())
        .build();
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoPilotDetails);
    when(pilotMapperMock.idsToClientRequest(spaceshipDtoWithNoPilotDetails.getCrewIds())).thenReturn(pilotClientRequest);
    when(pilotClientMock.getPilotsByIds(pilotClientRequest)).thenReturn(List.of(pilotClientResponse));
    when(pilotMapperMock.clientResponseToDto(pilotClientResponse)).thenReturn(pilotDto);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .contains(spaceshipDto);
    assertThat(actual.get(0).getCrew()).contains(pilotDto);
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
    final SpaceshipClientResponse spaceshipClientResponseWithNoPilots = spaceshipClientResponse.toBuilder()
        .crewIds(Collections.emptyList())
        .build();
    final SpaceshipDto spaceshipDtoWithNoPilots = spaceshipDto.toBuilder()
        .crewIds(Collections.emptyList())
        .crew(Collections.emptyList())
        .build();
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponseWithNoPilots));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponseWithNoPilots)).thenReturn(spaceshipDtoWithNoPilots);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .contains(spaceshipDtoWithNoPilots);
    assertThat(actual.get(0).getCrew()).isEmpty();
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponseWithNoPilots);
    verify(spaceshipClientMock).getSpaceships();
    verifyNoInteractions(pilotClientMock);
    verifyNoInteractions(pilotMapperMock);
  }

  @Test
  void getAll_noPilotsReturnedFromClient() {
    final SpaceshipDto spaceshipDtoWithNoPilotDetails = spaceshipDto.toBuilder()
        .crew(Collections.emptyList())
        .build();
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoPilotDetails);
    when(pilotMapperMock.idsToClientRequest(spaceshipDtoWithNoPilotDetails.getCrewIds())).thenReturn(pilotClientRequest);
    when(pilotClientMock.getPilotsByIds(pilotClientRequest)).thenReturn(Collections.emptyList());

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .contains(spaceshipDtoWithNoPilotDetails);
    assertThat(actual.get(0).getCrew()).isEmpty();
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(spaceshipClientMock).getSpaceships();
    verify(pilotMapperMock).idsToClientRequest(spaceshipDto.getCrewIds());
    verify(pilotClientMock).getPilotsByIds(pilotClientRequest);
    verify(pilotMapperMock, times(0)).clientResponseToDto(any());
  }
}
