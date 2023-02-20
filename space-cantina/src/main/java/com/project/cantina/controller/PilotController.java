package com.project.cantina.controller;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotIdsRequest;
import com.project.cantina.dto.PilotRequest;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.service.PilotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Tag(name = "pilots", description = "pilot operations")
@RestController
@RequestMapping("api/pilots")
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

  @Operation(summary = "Get all pilots by Ids",
      description = "Retrieves all pilots based on given Ids",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved list of pilots")
      })
  @PostMapping("/crew")
  public ResponseEntity<List<PilotResponse>> getPilotsByIds(@Valid @RequestBody final PilotIdsRequest pilotIdsRequest) {

    final List<PilotResponse> pilotResponses = pilotService.getAllByIds(pilotIdsRequest.getPilotUuids()).stream()
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
          @ApiResponse(responseCode = "200", description = "Successfully added a new pilot"),
          @ApiResponse(responseCode = "400", description = "Request data validation failed")
      })
  @PostMapping
  public ResponseEntity<PilotResponse> addPilot(@Valid @RequestBody final PilotRequest pilotRequest) {

    final PilotDto pilotDto = pilotMapper.requestToDto(pilotRequest);
    final PilotResponse pilotResponse = pilotMapper.dtoToResponse(pilotService.add(pilotDto));

    return ResponseEntity.ok(pilotResponse);
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
      @Valid @RequestBody final PilotRequest pilotRequest
  ) {

    final PilotDto pilotDtoUpdate = pilotMapper.requestToDto(pilotRequest);
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
}
