package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.entity.SpaceshipEntity;
import com.project.hangar.exception.NotFoundException;
import com.project.hangar.mapper.SpaceshipMapper;
import com.project.hangar.repository.SpaceshipRepository;
import com.project.hangar.service.impl.SpaceshipServiceImpl;
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

import static com.project.hangar.common.Constants.buildDto;
import static com.project.hangar.common.Constants.buildEntity;
import static com.project.hangar.common.Constants.getRandomUUID;
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
  private SpaceshipRepository spaceshipRepositoryMock;

  @Mock
  private SpaceshipMapper spaceshipMapperMock;

  private SpaceshipServiceImpl spaceshipService;

  private SpaceshipDto spaceshipDto;

  private SpaceshipEntity spaceshipEntity;

  private UUID randomId;

  @BeforeEach
  void setUp() {
    spaceshipService = new SpaceshipServiceImpl(spaceshipRepositoryMock, spaceshipMapperMock);
    spaceshipDto = buildDto();
    spaceshipEntity = buildEntity();
    randomId = getRandomUUID();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(spaceshipRepositoryMock, spaceshipMapperMock);
  }

  @Test
  void getAll() {
    when(spaceshipRepositoryMock.findAll()).thenReturn(List.of(spaceshipEntity));
    when(spaceshipMapperMock.entityToDto(spaceshipEntity)).thenReturn(spaceshipDto);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual)
        .hasSize(1)
        .containsExactly(spaceshipDto);
    verify(spaceshipRepositoryMock).findAll();
    verify(spaceshipMapperMock).entityToDto(spaceshipEntity);
  }

  @Test
  void getById() {
    when(spaceshipRepositoryMock.findById(spaceshipDto.getId())).thenReturn(Optional.of(spaceshipEntity));
    when(spaceshipMapperMock.entityToDto(spaceshipEntity)).thenReturn(spaceshipDto);

    final SpaceshipDto actual = spaceshipService.getById(spaceshipDto.getId());

    assertThat(actual).isEqualTo(spaceshipDto);
    verify(spaceshipRepositoryMock).findById(spaceshipDto.getId());
    verify(spaceshipMapperMock).entityToDto(spaceshipEntity);
  }

  @Test
  void getById_notFound() {
    when(spaceshipRepositoryMock.findById(randomId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> spaceshipService.getById(randomId));

    verify(spaceshipRepositoryMock).findById(randomId);
    verifyNoInteractions(spaceshipMapperMock);
  }

  @Test
  void add() {
    when(spaceshipMapperMock.dtoToEntity(spaceshipDto)).thenReturn(spaceshipEntity);
    when(spaceshipRepositoryMock.save(spaceshipEntity)).thenReturn(spaceshipEntity);
    when(spaceshipMapperMock.entityToDto(spaceshipEntity)).thenReturn(spaceshipDto);

    final SpaceshipDto actual = spaceshipService.add(spaceshipDto);

    assertThat(actual).isEqualTo(spaceshipDto);
    verify(spaceshipRepositoryMock).save(spaceshipEntity);
    verify(spaceshipMapperMock).entityToDto(spaceshipEntity);
    verify(spaceshipMapperMock).dtoToEntity(spaceshipDto);
  }

  @Test
  void updateById() {
    when(spaceshipRepositoryMock.findById(spaceshipDto.getId())).thenReturn(Optional.of(spaceshipEntity));
    when(spaceshipRepositoryMock.save(spaceshipEntity)).thenReturn(spaceshipEntity);
    when(spaceshipMapperMock.entityToDto(spaceshipEntity)).thenReturn(spaceshipDto);

    final SpaceshipDto actual = spaceshipService.updateById(spaceshipDto, spaceshipDto.getId());

    assertThat(actual).isEqualTo(spaceshipDto);
    verify(spaceshipRepositoryMock).findById(spaceshipDto.getId());
    verify(spaceshipRepositoryMock).save(spaceshipEntity);
    verify(spaceshipMapperMock).updateEntityWithDto(spaceshipEntity, spaceshipDto);
    verify(spaceshipMapperMock).entityToDto(spaceshipEntity);
  }

  @Test
  void updateById_notFound() {
    when(spaceshipRepositoryMock.findById(randomId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> spaceshipService.updateById(spaceshipDto, randomId));

    verify(spaceshipRepositoryMock).findById(randomId);
    verify(spaceshipRepositoryMock, times(0)).save(any());
    verifyNoInteractions(spaceshipMapperMock);
  }

  @Test
  void deleteById() {
    when(spaceshipRepositoryMock.findById(randomId)).thenReturn(Optional.of(spaceshipEntity));

    spaceshipService.deleteById(randomId);

    verify(spaceshipRepositoryMock).findById(randomId);
    verify(spaceshipRepositoryMock).deleteById(randomId);
  }

  @Test
  void deleteById_notFound() {
    when(spaceshipRepositoryMock.findById(randomId)).thenReturn(Optional.empty());

    assertThrows(NotFoundException.class, () -> spaceshipService.deleteById(randomId));

    verify(spaceshipRepositoryMock).findById(randomId);
  }
}
