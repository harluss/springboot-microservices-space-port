package com.project.hangar.controller;

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
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("api/spaceships")
public class SpaceshipController {

  private final SpaceshipService service;

  private final SpaceshipMapper mapper;

  public SpaceshipController(final SpaceshipService spaceshipService, final SpaceshipMapper spaceshipMapper) {
    this.service = spaceshipService;
    this.mapper = spaceshipMapper;
  }

  @GetMapping
  public ResponseEntity<List<SpaceshipResponse>> getSpaceships() {
    log.info("getting spaceships");

    final List<SpaceshipResponse> spaceshipResponses = service.getSpaceships()
        .stream()
        .map(mapper::spaceshipDtoToResponse)
        .toList();

    return ResponseEntity.ok(spaceshipResponses);
  }

  @GetMapping("{spaceshipId}")
  public ResponseEntity<SpaceshipResponse> getSpaceshipById(@PathVariable("spaceshipId") final UUID spaceshipId) {
    log.info("getting spaceship no. {}", spaceshipId);

    final SpaceshipResponse spaceshipResponse = mapper.spaceshipDtoToResponse(service.getSpaceshipById(spaceshipId));

    return ResponseEntity.ok(spaceshipResponse);
  }
}
