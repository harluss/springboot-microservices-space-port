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
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDto);
    when(pilotMapperMock.idsToClientRequest(spaceshipDto.getCrewIds())).thenReturn(pilotClientRequest);
    when(pilotClientMock.getPilotsByIds(pilotClientRequest)).thenReturn(List.of(pilotClientResponse));
    when(pilotMapperMock.clientResponseToDto(pilotClientResponse)).thenReturn(pilotDto);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .containsExactly(spaceshipDto);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(spaceshipClientMock).getSpaceships();
    verify(pilotMapperMock).idsToClientRequest(spaceshipDto.getCrewIds());
    verify(pilotClientMock).getPilotsByIds(pilotClientRequest);
    verify(pilotMapperMock).clientResponseToDto(pilotClientResponse);
  }
}
