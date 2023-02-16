package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.entity.SpaceshipEntity;
import com.project.hangar.exceptions.AlreadyExistsException;
import com.project.hangar.exceptions.NotFoundException;
import com.project.hangar.mapper.SpaceshipMapper;
import com.project.hangar.repository.SpaceshipRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class SpaceshipServiceImpl implements SpaceshipService {

  public static final String NOT_FOUND_MESSAGE = "Spaceship not found";

  public static final String NOT_FOUND_ID_PROVIDED_MESSAGE = "Spaceship with id {} not found";

  private final SpaceshipRepository spaceshipRepository;

  private final SpaceshipMapper spaceshipMapper;

  public SpaceshipServiceImpl(final SpaceshipRepository spaceshipRepository, final SpaceshipMapper spaceshipMapper) {
    this.spaceshipRepository = spaceshipRepository;
    this.spaceshipMapper = spaceshipMapper;
  }

  @Override
  public List<SpaceshipDto> getAll() {

    final List<SpaceshipDto> spaceshipDtos = spaceshipRepository.findAll().stream()
        .map(spaceshipMapper::entityToDto)
        .toList();
    log.info(spaceshipDtos.isEmpty() ? "No spaceships found" : "Spaceships found");

    return spaceshipDtos;
  }

  @Override
  public SpaceshipDto getById(final UUID id) {

    final SpaceshipDto spaceshipDto = spaceshipRepository.findById(id)
        .map(spaceshipMapper::entityToDto)
        .orElseThrow(() -> {
          log.info(NOT_FOUND_ID_PROVIDED_MESSAGE, id);
          throw new NotFoundException(NOT_FOUND_MESSAGE);
        });
    log.info("Spaceship with id {} found", id);

    return spaceshipDto;
  }

  @Override
  public SpaceshipDto add(final SpaceshipDto spaceshipDto) {

    final Optional<SpaceshipEntity> existingSpaceship = spaceshipRepository.findByName(spaceshipDto.getName());

    if (existingSpaceship.isPresent()) {
      log.info("Spaceship with name {} already exists", spaceshipDto.getName());
      throw new AlreadyExistsException("Spaceship with this name already exists");
    }

    final SpaceshipEntity savedSpaceship = spaceshipRepository.save(spaceshipMapper.dtoToEntity(spaceshipDto));
    log.info("Spaceship added: {}", savedSpaceship);

    return spaceshipMapper.entityToDto(savedSpaceship);
  }

  @Override
  @Transactional
  public SpaceshipDto updateById(final SpaceshipDto spaceshipDtoUpdate, final UUID id) {

    final SpaceshipEntity existingSpaceshipEntity = spaceshipRepository.findById(id)
        .orElseThrow(() -> {
          log.info(NOT_FOUND_ID_PROVIDED_MESSAGE, id);
          throw new NotFoundException(NOT_FOUND_MESSAGE);
        });

    spaceshipMapper.updateEntityWithDto(existingSpaceshipEntity, spaceshipDtoUpdate);
    final SpaceshipEntity updatedSpaceshipEntity = spaceshipRepository.save(existingSpaceshipEntity);
    final SpaceshipDto updatedSpaceshipDto = spaceshipMapper.entityToDto(updatedSpaceshipEntity);
    log.info("Spaceship updated: {}", updatedSpaceshipDto);

    return updatedSpaceshipDto;
  }

  @Override
  public void deleteById(final UUID id) {

    final Optional<SpaceshipEntity> existingSpaceship = spaceshipRepository.findById(id);

    if (existingSpaceship.isEmpty()) {
      log.info(NOT_FOUND_ID_PROVIDED_MESSAGE, id);
      throw new NotFoundException(NOT_FOUND_MESSAGE);
    }

    spaceshipRepository.deleteById(id);
    log.info("Spaceship with id {} deleted", id);
  }
}
