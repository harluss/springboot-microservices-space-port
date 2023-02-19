package com.project.cantina.service;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.entity.PilotEntity;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.repository.PilotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static com.project.cantina.common.Constants.buildDto;
import static com.project.cantina.common.Constants.buildEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class PilotServiceImplTest {

  @Mock
  private PilotMapper pilotMapperMock;

  @Mock
  private PilotRepository pilotRepositoryMock;

  private PilotServiceImpl pilotService;

  private PilotDto pilotDto;

  private PilotEntity pilotEntity;

  @BeforeEach
  void setUp() {
    pilotService = new PilotServiceImpl(pilotRepositoryMock, pilotMapperMock);
    pilotDto = buildDto();
    pilotEntity = buildEntity();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(pilotRepositoryMock, pilotMapperMock);
  }

  @Test
  void getAll() {
    when(pilotMapperMock.entityToDto(pilotEntity)).thenReturn(pilotDto);
    when(pilotRepositoryMock.findAll()).thenReturn(List.of(pilotEntity));

    final List<PilotDto> actual = pilotService.getAll();

    assertThat(actual)
        .hasSize(1)
        .containsExactly(pilotDto);
    verify(pilotMapperMock).entityToDto(pilotEntity);
    verify(pilotRepositoryMock).findAll();
  }
}
