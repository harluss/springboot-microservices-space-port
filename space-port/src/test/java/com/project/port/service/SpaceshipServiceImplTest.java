package com.project.port.service;

import static com.project.port.common.Constant.buildAddPilotClientRequest;
import static com.project.port.common.Constant.buildAddSpaceshipClientRequest;
import static com.project.port.common.Constant.buildPilotClientResponse;
import static com.project.port.common.Constant.buildPilotDto;
import static com.project.port.common.Constant.buildSpaceshipClientResponse;
import static com.project.port.common.Constant.buildSpaceshipClientResponseWithNoCrewIds;
import static com.project.port.common.Constant.buildSpaceshipDto;
import static com.project.port.common.Constant.buildSpaceshipDtoWithNoCrewDetails;
import static com.project.port.common.Constant.buildSpaceshipDtoWithNoCrewDetailsAndCrewIds;
import static com.project.port.common.Constant.buildUpdateSpaceshipClientRequest;
import static com.project.port.common.Constant.buildUpdateSpaceshipDtoWithNoId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.project.port.client.PilotClient;
import com.project.port.client.SpaceshipClient;
import com.project.port.dto.pilot.AddPilotClientRequest;
import com.project.port.dto.pilot.PilotClientResponse;
import com.project.port.dto.pilot.PilotDto;
import com.project.port.dto.spaceship.AddSpaceshipClientRequest;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.UpdateSpaceshipClientRequest;
import com.project.port.exception.AddPilotException;
import com.project.port.exception.NotFoundException;
import com.project.port.exception.PilotExistsException;
import com.project.port.mapper.PilotMapper;
import com.project.port.mapper.SpaceshipMapper;
import com.project.port.service.impl.SpaceshipServiceImpl;

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

  private SpaceshipDto spaceshipDtoWithNoCrewDetails;

  private SpaceshipDto spaceshipDtoWithNoCrewDetailsAndCrewIds;

  private SpaceshipClientResponse spaceshipClientResponse;

  private SpaceshipClientResponse spaceshipClientResponseWithNoCrewIds;

  private AddSpaceshipClientRequest addSpaceshipClientRequest;

  private UpdateSpaceshipClientRequest updateSpaceshipClientRequest;

  private SpaceshipDto updateSpaceshipDto;

  private PilotClientResponse pilotClientResponse;

  private AddPilotClientRequest addPilotClientRequest;

  private PilotDto pilotDto;

  @BeforeEach
  void setUp() {
    spaceshipService = new SpaceshipServiceImpl(spaceshipClientMock, pilotClientMock, spaceshipMapperMock,
        pilotMapperMock);
    spaceshipDto = buildSpaceshipDto();
    spaceshipDtoWithNoCrewDetails = buildSpaceshipDtoWithNoCrewDetails();
    spaceshipClientResponse = buildSpaceshipClientResponse();
    pilotClientResponse = buildPilotClientResponse();
    addPilotClientRequest = buildAddPilotClientRequest();
    pilotDto = buildPilotDto();
    spaceshipDtoWithNoCrewDetailsAndCrewIds = buildSpaceshipDtoWithNoCrewDetailsAndCrewIds();
    spaceshipClientResponseWithNoCrewIds = buildSpaceshipClientResponseWithNoCrewIds();
    addSpaceshipClientRequest = buildAddSpaceshipClientRequest();
    updateSpaceshipDto = buildUpdateSpaceshipDtoWithNoId();
    updateSpaceshipClientRequest = buildUpdateSpaceshipClientRequest();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(spaceshipClientMock, spaceshipMapperMock, pilotMapperMock, pilotClientMock);
  }

  @Test
  void getAll() {
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoCrewDetails);
    when(pilotClientMock.getPilots()).thenReturn(List.of(pilotClientResponse));
    when(pilotMapperMock.clientResponseToDto(pilotClientResponse)).thenReturn(pilotDto);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual).hasSize(1).contains(spaceshipDto);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(spaceshipClientMock).getSpaceships();
    verify(pilotClientMock).getPilots();
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
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponseWithNoCrewIds)).thenReturn(
        spaceshipDtoWithNoCrewDetailsAndCrewIds);

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual).hasSize(1).contains(spaceshipDtoWithNoCrewDetailsAndCrewIds);
    assertThat(actual.get(0).getCrew()).isNullOrEmpty();
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponseWithNoCrewIds);
    verify(spaceshipClientMock).getSpaceships();
    verifyNoInteractions(pilotClientMock);
    verifyNoInteractions(pilotMapperMock);
  }

  @Test
  void getAll_noPilotsReturnedFromClient() {
    when(spaceshipClientMock.getSpaceships()).thenReturn(List.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoCrewDetails);
    when(pilotClientMock.getPilots()).thenReturn(Collections.emptyList());

    final List<SpaceshipDto> actual = spaceshipService.getAll();

    assertThat(actual).hasSize(1).contains(spaceshipDtoWithNoCrewDetails);
    assertThat(actual.get(0).getCrew()).isEmpty();
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(spaceshipClientMock).getSpaceships();
    verify(pilotClientMock).getPilots();
    verify(pilotMapperMock, times(0)).clientResponseToDto(any());
  }

  @Test
  void getById() {
    final UUID reqId = spaceshipDtoWithNoCrewDetails.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDtoWithNoCrewDetails);
    when(pilotClientMock.getPilots()).thenReturn(List.of(pilotClientResponse));
    when(pilotMapperMock.clientResponseToDto(pilotClientResponse)).thenReturn(pilotDto);

    final SpaceshipDto actual = spaceshipService.getById(reqId);

    assertThat(actual).isEqualTo(spaceshipDto);
    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(pilotClientMock).getPilots();
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
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(
        Optional.ofNullable(spaceshipClientResponseWithNoCrewIds));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponseWithNoCrewIds)).thenReturn(
        spaceshipDtoWithNoCrewDetailsAndCrewIds);

    final SpaceshipDto actual = spaceshipService.getById(reqId);

    assertThat(actual).isEqualTo(spaceshipDtoWithNoCrewDetailsAndCrewIds);
    assertThat(actual.getCrew()).isNullOrEmpty();
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
    when(pilotClientMock.getPilots()).thenReturn(Collections.emptyList());

    final SpaceshipDto actual = spaceshipService.getById(reqId);

    assertThat(actual).isEqualTo(spaceshipDtoWithNoCrewDetails);
    assertThat(actual.getCrew()).isEmpty();
    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
    verify(pilotClientMock).getPilots();
    verify(pilotMapperMock, times(0)).clientResponseToDto(any());
  }

  @Test
  void add() {
    final PilotDto savedPilotDto = pilotDto.toBuilder().name("C3P0").build();
    final SpaceshipDto spaceshipDtoWithNoCrewIds = spaceshipDto.toBuilder().crewIds(null).build();
    when(pilotClientMock.getPilots()).thenReturn(Collections.emptyList());
    when(pilotMapperMock.dtoToClientAddRequest(pilotDto)).thenReturn(addPilotClientRequest);
    when(pilotClientMock.addPilot(addPilotClientRequest)).thenReturn(pilotClientResponse);
    when(pilotMapperMock.clientResponseToDto(pilotClientResponse)).thenReturn(savedPilotDto);
    when(spaceshipMapperMock.dtoToClientAddRequest(spaceshipDto)).thenReturn(addSpaceshipClientRequest);
    when(spaceshipClientMock.addSpaceship(addSpaceshipClientRequest)).thenReturn(spaceshipClientResponse);
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse)).thenReturn(spaceshipDto);

    final SpaceshipDto actual = spaceshipService.add(spaceshipDtoWithNoCrewIds);

    assertThat(actual).isEqualTo(spaceshipDto);
    assertThat(actual.getCrew()).containsExactly(savedPilotDto);
    assertThat(actual.getCrewIds()).containsExactly(savedPilotDto.getId());
    verify(pilotClientMock).getPilots();
    verify(pilotClientMock).addPilot(addPilotClientRequest);
    verify(pilotMapperMock).dtoToClientAddRequest(pilotDto);
    verify(pilotMapperMock).clientResponseToDto(pilotClientResponse);
    verify(spaceshipClientMock).addSpaceship(addSpaceshipClientRequest);
    verify(spaceshipMapperMock).dtoToClientAddRequest(spaceshipDtoWithNoCrewIds);
    verify(spaceshipMapperMock).clientResponseToDto(spaceshipClientResponse);
  }

  @Test
  void add_pilotsExist() {
    when(pilotClientMock.getPilots()).thenReturn(List.of(pilotClientResponse));

    assertThatThrownBy(() -> spaceshipService.add(spaceshipDto))
        .isInstanceOf(PilotExistsException.class)
        .hasMessageContaining(spaceshipDto.getCrew().get(0).getName());

    verify(pilotClientMock).getPilots();
    verify(pilotClientMock, times(0)).addPilot(any());
    verifyNoInteractions(pilotMapperMock);
    verifyNoInteractions(spaceshipMapperMock);
    verifyNoInteractions(spaceshipClientMock);
  }

  @Test
  void add_noPilotsSaved() {
    when(pilotClientMock.getPilots()).thenReturn(Collections.emptyList());
    when(pilotMapperMock.dtoToClientAddRequest(pilotDto)).thenReturn(addPilotClientRequest);
    when(pilotClientMock.addPilot(addPilotClientRequest)).thenReturn(null);

    assertThatThrownBy(() -> spaceshipService.add(spaceshipDto)).isInstanceOf(AddPilotException.class);

    verify(pilotClientMock).getPilots();
    verify(pilotClientMock).addPilot(addPilotClientRequest);
    verify(pilotMapperMock).dtoToClientAddRequest(pilotDto);
    verify(pilotMapperMock, times(0)).clientResponseToDto(any());
    verifyNoInteractions(spaceshipMapperMock);
    verifyNoInteractions(spaceshipClientMock);
  }

  @Test
  void updateById() {
    final UUID reqId = spaceshipDto.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.of(spaceshipClientResponse));
    when(spaceshipMapperMock.clientResponseToDto(spaceshipClientResponse))
        .thenReturn(spaceshipDto)
        .thenReturn(updateSpaceshipDto);
    when(spaceshipMapperMock.dtoToClientUpdateRequest(spaceshipDto)).thenReturn(updateSpaceshipClientRequest);
    when(spaceshipClientMock.updateSpaceshipById(reqId, updateSpaceshipClientRequest)).thenReturn(
        spaceshipClientResponse);

    final SpaceshipDto actual = spaceshipService.updateById(updateSpaceshipDto, reqId);

    System.out.println(">>> " + actual);
    System.out.println(">>> " + updateSpaceshipDto);

    assertThat(actual).isEqualTo(updateSpaceshipDto);
    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipClientMock).updateSpaceshipById(reqId, updateSpaceshipClientRequest);
    verify(spaceshipMapperMock, times(2)).clientResponseToDto(spaceshipClientResponse);
    verify(spaceshipMapperMock).updateDtoWithDto(spaceshipDto, updateSpaceshipDto);
    verify(spaceshipMapperMock).dtoToClientUpdateRequest(spaceshipDto);
  }

  @Test
  void updateById_notFound() {
    final UUID reqId = spaceshipDto.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> spaceshipService.updateById(updateSpaceshipDto, reqId)).isInstanceOf(
        NotFoundException.class);

    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipClientMock, times(0)).updateSpaceshipById(any(), any());
    verifyNoInteractions(spaceshipMapperMock);
  }

  @Test
  void deleteById() {
    final UUID reqId = spaceshipDto.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.of(spaceshipClientResponse));

    spaceshipService.deleteById(reqId);

    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipClientMock).deleteSpaceshipById(reqId);
    verify(pilotClientMock, times(spaceshipDto.getCrewIds().size())).deletePilotById(any());
  }

  @Test
  void deleteById_notFound() {
    final UUID reqId = spaceshipDto.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> spaceshipService.deleteById(reqId)).isInstanceOf(NotFoundException.class);

    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipClientMock, times(0)).deleteSpaceshipById(any());
    verify(pilotClientMock, times(0)).deletePilotById(any());
  }

  @Test
  void deleteById_noPilots() {
    final UUID reqId = spaceshipDto.getId();
    when(spaceshipClientMock.getSpaceshipById(reqId)).thenReturn(Optional.of(spaceshipClientResponseWithNoCrewIds));

    spaceshipService.deleteById(reqId);

    verify(spaceshipClientMock).getSpaceshipById(reqId);
    verify(spaceshipClientMock).deleteSpaceshipById(reqId);
    verify(pilotClientMock, times(0)).deletePilotById(any());
  }
}
