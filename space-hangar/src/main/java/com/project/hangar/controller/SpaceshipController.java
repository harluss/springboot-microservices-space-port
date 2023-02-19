package com.project.hangar.controller;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.mapper.SpaceshipMapper;
import com.project.hangar.service.SpaceshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Tag(name = "spaceships", description = "spaceships operations")
@RestController
@RequestMapping("api/spaceships")
public class SpaceshipController {

  private final SpaceshipService spaceshipService;

  private final SpaceshipMapper spaceshipMapper;

  public SpaceshipController(final SpaceshipService spaceshipService, final SpaceshipMapper spaceshipMapper) {
    this.spaceshipService = spaceshipService;
    this.spaceshipMapper = spaceshipMapper;
  }

  @Operation(summary = "Get all spaceships",
      description = "Retrieves all spaceships",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved list of spaceships")
      })
  @GetMapping
  public ResponseEntity<List<SpaceshipResponse>> getSpaceships() {

    final List<SpaceshipResponse> spaceshipResponses = spaceshipService.getAll()
        .stream()
        .map(spaceshipMapper::dtoToResponse)
        .toList();

    return ResponseEntity.ok(spaceshipResponses);
  }

  @Operation(summary = "Get spaceship by Id",
      description = "Retrieves a single spaceship based on given Id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved the requested spaceship"),
          @ApiResponse(responseCode = "404", description = "Could not find the requested spaceship")
      })
  @GetMapping("{id}")
  public ResponseEntity<SpaceshipResponse> getSpaceshipById(@NotBlank @PathVariable final UUID id) {

    final SpaceshipResponse spaceshipResponse = spaceshipMapper.dtoToResponse(spaceshipService.getById(id));

    return ResponseEntity.ok(spaceshipResponse);
  }

  @Operation(summary = "Add new spaceship",
      description = "Adds a new spaceship to the hangar",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully added a new spaceship"),
          @ApiResponse(responseCode = "400", description = "Request data validation failed")
      })
  @PostMapping
  public ResponseEntity<SpaceshipResponse> addSpaceship(@Valid @RequestBody final SpaceshipRequest spaceshipRequest) {

    final SpaceshipDto spaceshipDto = spaceshipMapper.requestToDto(spaceshipRequest);
    final SpaceshipResponse spaceshipResponse = spaceshipMapper.dtoToResponse(spaceshipService.add(spaceshipDto));

    return ResponseEntity.ok(spaceshipResponse);
  }

  @Operation(summary = "Update spaceship",
      description = "Updates details of existing spaceship based on given Id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully updated the requested spaceship"),
          @ApiResponse(responseCode = "404", description = "Could not find the requested spaceship")
      })
  @PutMapping("{id}")
  public ResponseEntity<SpaceshipResponse> updateSpaceshipById(
      @NotBlank @PathVariable final UUID id,
      @Valid @RequestBody final SpaceshipRequest spaceshipRequest
  ) {

    final SpaceshipDto spaceshipDtoUpdate = spaceshipMapper.requestToDto(spaceshipRequest);
    final SpaceshipResponse updatedSpaceshipResponse = spaceshipMapper.dtoToResponse(
        spaceshipService.updateById(spaceshipDtoUpdate, id)
    );

    return ResponseEntity.ok(updatedSpaceshipResponse);
  }

  @Operation(summary = "Delete spaceship",
      description = "Deletes existing spaceship based on given Id",
      responses = {
          @ApiResponse(responseCode = "204", description = "Successfully deleted the requested spaceship"),
          @ApiResponse(responseCode = "404", description = "Could not find the requested spaceship")
      })
  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteSpaceshipById(@NotBlank @PathVariable final UUID id) {

    spaceshipService.deleteById(id);

    return ResponseEntity.noContent().build();
  }
}
