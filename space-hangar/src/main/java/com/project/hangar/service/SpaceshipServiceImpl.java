package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.entity.SpaceshipEntity;
import com.project.hangar.mapper.SpaceshipMapper;
import com.project.hangar.repository.SpaceshipRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class SpaceshipServiceImpl implements SpaceshipService {

  private final SpaceshipRepository spaceshipRepository;

  private final SpaceshipMapper spaceshipMapper;

  public SpaceshipServiceImpl(final SpaceshipRepository spaceshipRepository, final SpaceshipMapper spaceshipMapper) {
    this.spaceshipRepository = spaceshipRepository;
    this.spaceshipMapper = spaceshipMapper;
  }

  @Override
  public List<SpaceshipDto> getAll() {

    return spaceshipRepository.findAll().stream()
        .map(spaceshipMapper::entityToDto)
        .toList();
  }

  @Override
  public SpaceshipDto getById(final UUID id) {

    return spaceshipRepository.findById(id)
        .map(spaceshipMapper::entityToDto)
        .orElseThrow(() -> {
          log.info("Spaceship with id {} not found", id);
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Spaceship not found");
        });
  }

  @Override
  public SpaceshipDto add(final SpaceshipDto spaceshipDto) {

    final Optional<SpaceshipEntity> existingSpaceship = spaceshipRepository.findByName(spaceshipDto.getName());

    if (existingSpaceship.isPresent()) {
      log.info("Spaceship with name {} already exists", spaceshipDto.getName());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Spaceship with this name already exists");
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
          log.info("Spaceship with id {} not found", id);
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Spaceship not found");
        });

    spaceshipMapper.updateEntityWithDto(existingSpaceshipEntity, spaceshipDtoUpdate);
    final SpaceshipEntity updatedSpaceshipEntity = spaceshipRepository.save(existingSpaceshipEntity);

    return spaceshipMapper.entityToDto(updatedSpaceshipEntity);
  }

  @Override
  public void deleteById(final UUID id) {

    final Optional<SpaceshipEntity> existingSpaceship = spaceshipRepository.findById(id);

    if (existingSpaceship.isEmpty()) {
      log.info("Spaceship with id {} not found", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Spaceship not found");
    }

    spaceshipRepository.deleteById(id);
    log.info("Spaceship with id {} deleted!", id);
  }
}
