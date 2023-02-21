package com.project.port.mapper;

import com.project.port.dto.SpaceshipClientResponse;
import com.project.port.dto.SpaceshipDto;
import com.project.port.dto.SpaceshipResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static com.project.port.common.Constant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
    final SpaceshipDto expected = buildSpaceshipDto().toBuilder()
        .crew(null)
        .build();

    final SpaceshipDto actual = spaceshipMapper.clientResponseToDto(clientResponse);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void dtoToResponse() {
    final SpaceshipDto dto = buildSpaceshipDto();
    final SpaceshipResponse expected = buildSpaceshipResponse();
    when(pilotMapperMock.dtoToResponse(dto.getCrew().get(0))).thenReturn(buildPilotResponse());

    final SpaceshipResponse actual = spaceshipMapper.dtoToResponse(dto);

    assertThat(actual).isEqualTo(expected);
    verify(pilotMapperMock).dtoToResponse(dto.getCrew().get(0));
  }
}
