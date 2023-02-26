package com.project.hangar.controller;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.mapper.SpaceshipMapper;
import com.project.hangar.service.SpaceshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Tag(name = "spaceships", description = "spaceships operations")
@RestController
@RequestMapping("api/v1/spaceships")
public class SpaceshipController {

  private final SpaceshipService spaceshipService;

  private final SpaceshipMapper spaceshipMapper;

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
  public ResponseEntity<SpaceshipResponse> getSpaceshipById(@NotBlank @PathVariable("id") final UUID spaceshipId) {

    final SpaceshipResponse spaceshipResponse = spaceshipMapper.dtoToResponse(spaceshipService.getById(spaceshipId));

    return ResponseEntity.ok(spaceshipResponse);
  }

  @Operation(summary = "Add new spaceship",
      description = "Adds a new spaceship to the hangar",
      responses = {
          @ApiResponse(responseCode = "201", description = "Successfully added a new spaceship"),
          @ApiResponse(responseCode = "400", description = "Request data validation failed")
      })
  @PostMapping
  public ResponseEntity<SpaceshipResponse> addSpaceship(@Valid @RequestBody final SpaceshipRequest spaceshipRequest) {

    final SpaceshipDto spaceshipDto = spaceshipMapper.requestToDto(spaceshipRequest);
    final SpaceshipResponse spaceshipResponse = spaceshipMapper.dtoToResponse(spaceshipService.add(spaceshipDto));
    final URI location = getResourceLocation(spaceshipResponse);

    return ResponseEntity.created(location).body(spaceshipResponse);
  }

  @Operation(summary = "Update spaceship",
      description = "Updates details of existing spaceship based on given Id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully updated the requested spaceship"),
          @ApiResponse(responseCode = "400", description = "Request data validation failed"),
          @ApiResponse(responseCode = "404", description = "Could not find the requested spaceship")
      })
  @PutMapping("{id}")
  public ResponseEntity<SpaceshipResponse> updateSpaceshipById(
      @NotBlank @PathVariable("id") final UUID spaceshipId,
      @Valid @RequestBody final SpaceshipRequest spaceshipRequest
  ) {

    final SpaceshipDto spaceshipDtoUpdate = spaceshipMapper.requestToDto(spaceshipRequest);
    final SpaceshipResponse updatedSpaceshipResponse = spaceshipMapper.dtoToResponse(
        spaceshipService.updateById(spaceshipDtoUpdate, spaceshipId));

    return ResponseEntity.ok(updatedSpaceshipResponse);
  }

  @Operation(summary = "Delete spaceship",
      description = "Deletes existing spaceship based on given Id",
      responses = {
          @ApiResponse(responseCode = "204", description = "Successfully deleted the requested spaceship"),
          @ApiResponse(responseCode = "404", description = "Could not find the requested spaceship")
      })
  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteSpaceshipById(@NotBlank @PathVariable("id") final UUID spaceshipId) {

    spaceshipService.deleteById(spaceshipId);

    return ResponseEntity.noContent().build();
  }

  private static URI getResourceLocation(final SpaceshipResponse spaceshipResponse) {
    return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(spaceshipResponse.getId()).toUri();
  }
}
