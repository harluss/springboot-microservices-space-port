package com.project.port.controller;

import com.project.port.dto.SpaceshipResponse;
import com.project.port.mapper.SpaceshipMapper;
import com.project.port.service.SpaceshipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@Tag(name = "spaceships", description = "spaceships operations")
@RestController
@RequestMapping("api/v1/spaceships")
public class SpaceshipController {

  private final SpaceshipService spaceshipService;

  private final SpaceshipMapper spaceshipMapper;

  @Operation(summary = "Get all spaceships and their crews",
      description = "Retrieves all spaceships and their crews",
      responses = {
          @ApiResponse(responseCode = "200", description = "Successfully retrieved list of spaceships with their crews")
      })
  @GetMapping
  public ResponseEntity<List<SpaceshipResponse>> getSpaceships() {

    final List<SpaceshipResponse> spaceshipResponses = spaceshipService.getAll()
        .stream()
        .map(spaceshipMapper::dtoToResponse)
        .toList();

    return ResponseEntity.ok(spaceshipResponses);
  }
}
