package com.project.port.service;

import com.project.port.client.PilotClient;
import com.project.port.client.SpaceshipClient;
import com.project.port.dto.SpaceshipClientResponse;
import com.project.port.dto.SpaceshipDto;
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

import static com.project.port.common.Constant.buildSpaceshipClientResponse;
import static com.project.port.common.Constant.buildSpaceshipDto;
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

  @BeforeEach
  void setUp() {
    spaceshipService = new SpaceshipServiceImpl(spaceshipClientMock, pilotClientMock, spaceshipMapperMock, pilotMapperMock);
    spaceshipDto = buildSpaceshipDto();
    spaceshipClientResponse = buildSpaceshipClientResponse();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(spaceshipClientMock, spaceshipMapperMock);
  }

  @Test
  void getAll() {
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDto);
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponse));

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .containsExactly(spaceshipDto);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(spaceshipClientMock).getSpaceships();
  }
}
