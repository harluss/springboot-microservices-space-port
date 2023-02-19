package com.project.cantina.controller;

import com.project.cantina.dto.PilotResponse;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.service.PilotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
