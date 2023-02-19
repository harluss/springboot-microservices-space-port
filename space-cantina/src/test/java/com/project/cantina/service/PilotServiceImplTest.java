package com.project.cantina.service;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.entity.PilotEntity;
import com.project.cantina.exception.NotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static com.project.cantina.common.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

  private UUID randomId;

  @BeforeEach
  void setUp() {
    pilotService = new PilotServiceImpl(pilotRepositoryMock, pilotMapperMock);
    pilotDto = buildDto();
    pilotEntity = buildEntity();
    randomId = getRandomUUID();
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

  @Test
  void getById() {
    when(pilotRepositoryMock.findById(pilotDto.getId())).thenReturn(Optional.of(pilotEntity));
    when(pilotMapperMock.entityToDto(pilotEntity)).thenReturn(pilotDto);

    final PilotDto actual = pilotService.getById(pilotDto.getId());

    assertThat(actual).isEqualTo(pilotDto);
    verify(pilotRepositoryMock).findById(pilotDto.getId());
    verify(pilotMapperMock).entityToDto(pilotEntity);
  }

  @Test
  void getById_notFound() {
    when(pilotRepositoryMock.findById(randomId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> pilotService.getById(randomId));

    verify(pilotRepositoryMock).findById(randomId);
    verifyNoInteractions(pilotMapperMock);
  }

  @Test
  void add() {
    when(pilotMapperMock.dtoToEntity(pilotDto)).thenReturn(pilotEntity);
    when(pilotRepositoryMock.save(pilotEntity)).thenReturn(pilotEntity);
    when(pilotMapperMock.entityToDto(pilotEntity)).thenReturn(pilotDto);

    final PilotDto actual = pilotService.add(pilotDto);

    assertThat(actual).isEqualTo(pilotDto);
    verify(pilotRepositoryMock).save(pilotEntity);
    verify(pilotMapperMock).entityToDto(pilotEntity);
    verify(pilotMapperMock).dtoToEntity(pilotDto);
  }
}
