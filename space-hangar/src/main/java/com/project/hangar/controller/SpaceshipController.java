package com.project.hangar.controller;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.service.SpaceshipMapper;
import com.project.hangar.service.SpaceshipService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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

  // todo: update swagger details
  // todo: add request validation
  // todo: add entity / db validation
  // todo: add readme
  // todo: fix CVEs
  // todo: add exception handling?

  @GetMapping
  public ResponseEntity<List<SpaceshipResponse>> getSpaceships() {
    log.info("getting spaceships");

    final List<SpaceshipResponse> spaceshipResponses = service.getSpaceships()
        .stream()
        .map(mapper::spaceshipDtoToResponse)
        .toList();

    return ResponseEntity.ok(spaceshipResponses);
  }

  @GetMapping("{id}")
  public ResponseEntity<SpaceshipResponse> getSpaceshipById(@NotBlank @PathVariable("id") final UUID spaceshipId) {
    log.info("getting spaceship no. {}", spaceshipId);

    final SpaceshipResponse spaceshipResponse = mapper.spaceshipDtoToResponse(service.getSpaceshipById(spaceshipId));

    return ResponseEntity.ok(spaceshipResponse);
  }

  @PostMapping
  public ResponseEntity<SpaceshipResponse> addSpaceship(@Valid @RequestBody final SpaceshipRequest spaceshipRequest) {
    log.info("adding spaceship: {}", spaceshipRequest);

    final SpaceshipDto spaceshipDto = mapper.spaceshipRequestToDto(spaceshipRequest);
    final SpaceshipResponse spaceshipResponse = mapper.spaceshipDtoToResponse(service.addSpaceship(spaceshipDto));

    return ResponseEntity.ok(spaceshipResponse);
  }

  @PutMapping("{id}")
  public ResponseEntity<SpaceshipResponse> updateSpaceshipById(@NotBlank @PathVariable("id") final UUID spaceshipId,
                                                               @Valid @RequestBody final SpaceshipRequest spaceshipRequest) {
    log.info("updating spaceship: {}", spaceshipRequest);

    final SpaceshipDto spaceshipDtoUpdate = mapper.spaceshipRequestToDto(spaceshipRequest);
    final SpaceshipResponse updatedSpaceshipResponse = mapper.spaceshipDtoToResponse(service.updateSpaceshipById(spaceshipDtoUpdate, spaceshipId));

    return ResponseEntity.ok(updatedSpaceshipResponse);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteSpaceshipById(@NotBlank @PathVariable("id") final UUID spaceshipId) {
    log.info("deleting spaceship no. {}", spaceshipId);

    service.deleteSpaceshipById(spaceshipId);

    return ResponseEntity.noContent().build();
  }
}
