package com.project.cantina.controller;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotRequest;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.service.PilotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Tag(name = "pilots", description = "pilot operations")
@RestController
@RequestMapping("api/pilots")
public class PilotController {

  private final PilotService pilotService;

  private final PilotMapper pilotMapper;

  public PilotController(final PilotService pilotService, final PilotMapper pilotMapper) {
    this.pilotService = pilotService;
    this.pilotMapper = pilotMapper;
  }

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
  public ResponseEntity<PilotResponse> getPilotById(@NotBlank @PathVariable final UUID id) {

    final PilotResponse pilotResponse = pilotMapper.dtoToResponse(pilotService.getById(id));

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
}
