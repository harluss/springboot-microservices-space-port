package com.project.port.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.port.dto.spaceship.AddSpaceshipRequest;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.SpaceshipResponse;
import com.project.port.dto.spaceship.UpdateSpaceshipRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipResponse;
import com.project.port.mapper.SpaceshipMapper;
import com.project.port.service.SpaceshipService;

@Log4j2
@CrossOrigin(origins = "${cors.enable.api-gateway.base-url}")
@Tag(name = "spaceships", description = "spaceships operations")
@RestController
@RequestMapping("api/v1/spaceships")
@RequiredArgsConstructor
public class SpaceshipController {

  private final SpaceshipService spaceshipService;

  private final SpaceshipMapper spaceshipMapper;

  @Operation(summary = "Get all spaceships and their crews", description = "Retrieves all spaceships and their crews", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved list of spaceships with their crews") })
  @GetMapping
  public ResponseEntity<List<SpaceshipResponse>> getSpaceships() {

    final List<SpaceshipResponse> spaceshipResponses = spaceshipService
        .getAll()
        .stream()
        .map(spaceshipMapper::dtoToResponse)
        .toList();

    return ResponseEntity.ok(spaceshipResponses);
  }

  @Operation(summary = "Get spaceship and its crew by Id", description = "Retrieves a single spaceship and its crew by a given spaceship Id", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the requested spaceship"),
      @ApiResponse(responseCode = "404", description = "Could not find the requested spaceship") })
  @GetMapping("{id}")
  public ResponseEntity<SpaceshipResponse> getSpaceshipById(@NotBlank @PathVariable("id") final UUID spaceshipId) {

    final SpaceshipResponse spaceshipResponse = spaceshipMapper.dtoToResponse(spaceshipService.getById(spaceshipId));

    return ResponseEntity.ok(spaceshipResponse);
  }

  @Operation(summary = "Add new spaceship with crew", description = "Adds a new spaceship to the hangar and its crew to the cantina", responses = {
      @ApiResponse(responseCode = "201", description = "Successfully added a new spaceship"),
      @ApiResponse(responseCode = "400", description = "Request data validation failed") })
  @PostMapping
  public ResponseEntity<SpaceshipResponse> addSpaceship(
      @Valid @RequestBody final AddSpaceshipRequest addSpaceshipRequest) {

    final SpaceshipDto spaceshipDto = spaceshipMapper.addRequestToDto(addSpaceshipRequest);
    final SpaceshipResponse spaceshipResponse = spaceshipMapper.dtoToResponse(spaceshipService.add(spaceshipDto));
    final URI location = getResourceLocation(spaceshipResponse);

    return ResponseEntity.created(location).body(spaceshipResponse);
  }

  @Operation(summary = "Update spaceship", description = "Updates details of existing spaceship based on given Id", responses = {
      @ApiResponse(responseCode = "200", description = "Successfully updated the requested spaceship"),
      @ApiResponse(responseCode = "400", description = "Request data validation failed"),
      @ApiResponse(responseCode = "404", description = "Could not find the requested spaceship") })
  @PutMapping("{id}")
  public ResponseEntity<UpdateSpaceshipResponse> updateSpaceshipById(
      @NotBlank @PathVariable("id") final UUID spaceshipId,
      @Valid @RequestBody final UpdateSpaceshipRequest updateSpaceshipRequest) {

    final SpaceshipDto updateSpaceshipDto = spaceshipMapper.updateRequestToDto(updateSpaceshipRequest);
    final UpdateSpaceshipResponse updatedSpaceshipResponse = spaceshipMapper.dtoToUpdateResponse(
        spaceshipService.updateById(updateSpaceshipDto, spaceshipId));

    return ResponseEntity.ok(updatedSpaceshipResponse);
  }

  private static URI getResourceLocation(final SpaceshipResponse spaceshipResponse) {
    return ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(spaceshipResponse.getId())
        .toUri();
  }
}
