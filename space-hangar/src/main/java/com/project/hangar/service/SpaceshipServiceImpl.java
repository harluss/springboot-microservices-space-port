package com.project.hangar.service;

import com.project.hangar.dto.SpaceshipDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Log4j2
@Service
public class SpaceshipServiceImpl implements SpaceshipService {

  private final List<SpaceshipDto> spaceships = List.of(
      SpaceshipDto.builder().id(1).crew(1).name("Razor Crest").payload(70).build(),
      SpaceshipDto.builder().id(2).crew(1).name("Slave I").payload(40).build(),
      SpaceshipDto.builder().id(3).crew(4).name("Millenium Falcon").payload(100).build()
  );

  @Override
  public List<SpaceshipDto> getSpaceships() {
    return spaceships;
  }

  @Override
  public SpaceshipDto getSpaceshipById(final Integer spaceshipId) {
    return spaceships.stream()
        .filter(spaceshipDto -> Objects.equals(spaceshipDto.getId(), spaceshipId))
        .findFirst()
        .orElseThrow(() -> {
          log.info("Spaceship no. {} not found!", spaceshipId);
          throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
  }
}
