package com.project.cantina.controller;

import com.project.cantina.dto.AddPilotRequest;
import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.dto.UpdatePilotRequest;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.service.PilotService;
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
@Tag(name = "pilots", description = "pilot operations")
@RestController
@RequestMapping("api/v1/pilots")
public class PilotController {

  private final PilotService pilotService;

  private final PilotMapper pilotMapper;

  @Operation(summary = "Get all pilots",
      description = "Retrieves all pilots",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved list of pilots")
      })
  @GetMapping
  public ResponseEntity<List<PilotResponse>> getPilots() {

    final List<PilotResponse> pilotResponses = pilotService.getAll().stream()
        .map(pilotMapper::dtoToResponse)
        .toList();

    return ResponseEntity.ok(pilotResponses);
  }

  @Operation(summary = "Get pilot by Id",
      description = "Retrieves a single pilot based on given Id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved the requested pilot"),
          @ApiResponse(responseCode = "404", description = "Could not find the requested pilot")
      })
  @GetMapping("{id}")
  public ResponseEntity<PilotResponse> getPilotById(@NotBlank @PathVariable("id") final UUID pilotId) {

    final PilotResponse pilotResponse = pilotMapper.dtoToResponse(pilotService.getById(pilotId));

    return ResponseEntity.ok(pilotResponse);
  }

  @Operation(summary = "Add new pilot",
      description = "Adds a new pilot to the cantina",
      responses = {
          @ApiResponse(responseCode = "201", description = "Successfully added a new pilot"),
          @ApiResponse(responseCode = "400", description = "Request data validation failed")
      })
  @PostMapping
  public ResponseEntity<PilotResponse> addPilot(@Valid @RequestBody final AddPilotRequest addPilotRequest) {

    final PilotDto pilotDto = pilotMapper.addRequestToDto(addPilotRequest);
    final PilotResponse pilotResponse = pilotMapper.dtoToResponse(pilotService.add(pilotDto));
    final URI location = getResourceLocation(pilotResponse);

    return ResponseEntity.created(location).body(pilotResponse);
  }

  @Operation(summary = "Update pilot",
      description = "Updates details of existing pilot based on given Id",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully updated the requested pilot"),
          @ApiResponse(responseCode = "400", description = "Request data validation failed"),
          @ApiResponse(responseCode = "404", description = "Could not find the requested pilot")
      })
  @PutMapping("{id}")
  public ResponseEntity<PilotResponse> updatePilotById(
      @NotBlank @PathVariable("id") final UUID pilotId,
      @Valid @RequestBody final UpdatePilotRequest updatePilotRequest
  ) {

    final PilotDto pilotDtoUpdate = pilotMapper.updateRequestToDto(updatePilotRequest);
    final PilotResponse updatedPilotResponse = pilotMapper.dtoToResponse(pilotService.updateById(pilotDtoUpdate, pilotId));

    return ResponseEntity.ok(updatedPilotResponse);
  }

  @Operation(summary = "Delete pilot",
      description = "Deletes existing pilot based on given Id",
      responses = {
          @ApiResponse(responseCode = "204", description = "Successfully deleted the requested pilot"),
          @ApiResponse(responseCode = "404", description = "Could not find the requested pilot")
      })
  @DeleteMapping("{id}")
  public ResponseEntity<Void> deletePilotById(@NotBlank @PathVariable("id") final UUID pilotId) {

    pilotService.deleteById(pilotId);

    return ResponseEntity.noContent().build();
  }

  private static URI getResourceLocation(final PilotResponse pilotResponse) {
    return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(pilotResponse.getId()).toUri();
  }
}
