package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.entity.SpaceshipEntity;
import com.project.hangar.exception.NotFoundException;
import com.project.hangar.mapper.SpaceshipMapper;
import com.project.hangar.repository.SpaceshipRepository;
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

import static com.project.hangar.common.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class SpaceshipServiceImplTest {

  @Mock
  private SpaceshipRepository repositoryMock;

  @Mock
  private SpaceshipMapper mapperMock;

  private SpaceshipServiceImpl spaceshipService;

  private SpaceshipDto spaceshipDto;

  private SpaceshipEntity spaceshipEntity;

  private UUID randomId;

  @BeforeEach
  void setUp() {
    spaceshipService = new SpaceshipServiceImpl(repositoryMock, mapperMock);
    spaceshipDto = buildDto();
    spaceshipEntity = buildEntity();
    randomId = getRandomUUID();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(repositoryMock, mapperMock);
  }

  @Test
  void getAll() {
    when(repositoryMock.findAll()).thenReturn(List.of(spaceshipEntity));
    when(mapperMock.entityToDto(spaceshipEntity)).thenReturn(spaceshipDto);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .containsExactly(spaceshipDto);
    verify(repositoryMock).findAll();
    verify(mapperMock).entityToDto(spaceshipEntity);
  }

  @Test
  void getById() {
    when(repositoryMock.findById(spaceshipDto.getId())).thenReturn(Optional.of(spaceshipEntity));
    when(mapperMock.entityToDto(spaceshipEntity)).thenReturn(spaceshipDto);

    final SpaceshipDto actual = spaceshipService.getById(spaceshipDto.getId());

    assertThat(actual).isEqualTo(spaceshipDto);
    verify(repositoryMock).findById(spaceshipDto.getId());
    verify(mapperMock).entityToDto(spaceshipEntity);
  }

  @Test
  void getById_notFound() {
    when(repositoryMock.findById(randomId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> spaceshipService.getById(randomId));

    verify(repositoryMock).findById(randomId);
    verifyNoInteractions(mapperMock);
  }

  @Test
  void add() {
    when(mapperMock.dtoToEntity(spaceshipDto)).thenReturn(spaceshipEntity);
    when(repositoryMock.save(spaceshipEntity)).thenReturn(spaceshipEntity);
    when(mapperMock.entityToDto(spaceshipEntity)).thenReturn(spaceshipDto);

    final SpaceshipDto actual = spaceshipService.add(spaceshipDto);

    assertThat(actual).isEqualTo(spaceshipDto);
    verify(repositoryMock).save(spaceshipEntity);
    verify(mapperMock).entityToDto(spaceshipEntity);
    verify(mapperMock).dtoToEntity(spaceshipDto);
  }

  @Test
  void updateById() {
    when(repositoryMock.findById(spaceshipDto.getId())).thenReturn(Optional.of(spaceshipEntity));
    when(repositoryMock.save(spaceshipEntity)).thenReturn(spaceshipEntity);
    when(mapperMock.entityToDto(spaceshipEntity)).thenReturn(spaceshipDto);

    final SpaceshipDto actual = spaceshipService.updateById(spaceshipDto, spaceshipDto.getId());

    assertThat(actual).isEqualTo(spaceshipDto);
    verify(repositoryMock).findById(spaceshipDto.getId());
    verify(repositoryMock).save(spaceshipEntity);
    verify(mapperMock).updateEntityWithDto(spaceshipEntity, spaceshipDto);
    verify(mapperMock).entityToDto(spaceshipEntity);
  }

  @Test
  void updateById_notFound() {
    when(repositoryMock.findById(randomId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> spaceshipService.updateById(spaceshipDto, randomId));

    verify(repositoryMock).findById(randomId);
    verify(repositoryMock, times(0)).save(any());
    verifyNoInteractions(mapperMock);
  }

  @Test
  void deleteById() {
    when(repositoryMock.findById(randomId)).thenReturn(Optional.of(spaceshipEntity));

    spaceshipService.deleteById(randomId);

    verify(repositoryMock).findById(randomId);
    verify(repositoryMock).deleteById(randomId);
  }

  @Test
  void deleteById_notFound() {
    when(repositoryMock.findById(randomId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> spaceshipService.deleteById(randomId));

    verify(repositoryMock).findById(randomId);
  }
}
