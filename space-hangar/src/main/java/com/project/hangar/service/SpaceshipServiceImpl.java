package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.entity.SpaceshipEntity;
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

  private final SpaceshipRepository repository;

  private final SpaceshipMapper mapper;

  public SpaceshipServiceImpl(final SpaceshipRepository repository, final SpaceshipMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public List<SpaceshipDto> getSpaceships() {

    return repository.findAll().stream()
        .map(mapper::spaceshipEntityToDto)
        .toList();
  }

  @Override
  public SpaceshipDto getSpaceshipById(final UUID spaceshipId) {

    return repository.findById(spaceshipId)
        .map(mapper::spaceshipEntityToDto)
        .orElseThrow(() -> {
          log.info("Spaceship no. {} not found", spaceshipId);
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Spaceship not found");
        });
  }

  @Override
  public SpaceshipDto addSpaceship(final SpaceshipDto spaceshipDto) {

    final Optional<SpaceshipEntity> existingSpaceship = repository.findByName(spaceshipDto.getName());

    if (existingSpaceship.isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Spaceship already exists");
    }

    final SpaceshipEntity savedSpaceship = repository.save(mapper.spaceshipDtoToEntity(spaceshipDto));
    log.info("Spaceship added: {}", savedSpaceship);

    return mapper.spaceshipEntityToDto(savedSpaceship);
  }

  @Override
  @Transactional
  public SpaceshipDto updateSpaceshipById(final SpaceshipDto spaceshipDtoUpdate, final UUID spaceshipId) {

    final SpaceshipEntity existingSpaceshipEntity = repository.findById(spaceshipId)
        .orElseThrow(() -> {
          log.info("Spaceship no. {} not found", spaceshipId);
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Spaceship not found");
        });

    mapper.spaceshipEntityUpdateWithDto(existingSpaceshipEntity, spaceshipDtoUpdate);
    final SpaceshipEntity updatedSpaceshipEntity = repository.save(existingSpaceshipEntity);

    return mapper.spaceshipEntityToDto(updatedSpaceshipEntity);
  }

  @Override
  public void deleteSpaceshipById(final UUID spaceshipId) {

    final Optional<SpaceshipEntity> existingSpaceship = repository.findById(spaceshipId);

    if (existingSpaceship.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Spaceship not found");
    }

    repository.deleteById(spaceshipId);
    log.info("Spaceship no. {} deleted!", spaceshipId);
  }
}
