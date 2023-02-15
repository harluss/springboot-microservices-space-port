package com.project.hangar.controller;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.service.SpaceshipMapper;
import com.project.hangar.service.SpaceshipService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("api/spaceships")
public class SpaceshipController {

  private final SpaceshipService spaceshipService;

  private final SpaceshipMapper spaceshipMapper;

  public SpaceshipController(final SpaceshipService spaceshipService, final SpaceshipMapper spaceshipMapper) {
    this.spaceshipService = spaceshipService;
    this.spaceshipMapper = spaceshipMapper;
  }

  @GetMapping
  public ResponseEntity<List<SpaceshipResponse>> getSpaceships() {
    log.info("getting spaceships");

    final List<SpaceshipResponse> spaceshipResponses = spaceshipService.getSpaceships()
        .stream()
        .map(spaceshipMapper::spaceshipDtoToResponse)
        .toList();

    return ResponseEntity.ok(spaceshipResponses);
  }

  @GetMapping("{spaceshipId}")
  public ResponseEntity<SpaceshipResponse> getSpaceshipById(@PathVariable("spaceshipId") final Integer spaceshipId) {
    log.info("getting spaceship no. {}", spaceshipId);

    final SpaceshipDto spaceshipDto = spaceshipService.getSpaceshipById(spaceshipId);
    final SpaceshipResponse spaceshipResponse = spaceshipMapper.spaceshipDtoToResponse(spaceshipDto);

    return ResponseEntity.ok(spaceshipResponse);
  }
}
