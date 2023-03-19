package com.project.port.common;

import com.project.port.dto.pilot.AddPilotClientRequest;
import com.project.port.dto.pilot.AddPilotRequest;
import com.project.port.dto.pilot.PilotClientResponse;
import com.project.port.dto.pilot.PilotDto;
import com.project.port.dto.pilot.PilotResponse;
import com.project.port.dto.spaceship.AddSpaceshipClientRequest;
import com.project.port.dto.spaceship.AddSpaceshipRequest;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.SpaceshipResponse;
import com.project.port.exception.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Constant {

  private static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

  private static final String SPACESHIP_NAME = "Tie Fighter";

  private static final String SPACESHIP_CLASS_TYPE = "Starfighter";

  private static final int SPACESHIP_PAYLOAD = 10;

  private static final int SPACESHIP_MAX_SPEED = 1200;

  private static final List<String> SPACESHIP_ARMAMENT = List.of("Laser cannons");

  private static final String PILOT_NAME = "Darth Vader";

  private static final String NEW_PILOT_NAME = "Darth Sidious";

  private static final String PILOT_SPECIES = "Human";

  private static final String PILOT_PROFESSION = "Sith Lord";

  private static final List<String> PILOT_WEAPONS = List.of("Lightsaber");

  private static final String SPACESHIP_NOT_FOUND = "Spaceship not found";

  private static final String PILOT_EXISTS = "Pilots already exist";

  public static SpaceshipDto buildSpaceshipDto() {
    final PilotDto pilotDto = buildPilotDto();

    return SpaceshipDto.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(List.of(pilotDto))
        .crewIds(List.of(ID))
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static SpaceshipDto buildSpaceshipDtoWithNoCrewIds() {
    final PilotDto pilotDto = buildPilotDto();

    return SpaceshipDto.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(List.of(pilotDto))
        .crewIds(null)
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static SpaceshipDto buildSpaceshipDtoWithNoCrewDetails() {
    return SpaceshipDto.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(null)
        .crewIds(List.of(ID))
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static SpaceshipDto buildSpaceshipDtoWithNoCrewDetailsAndCrewIds() {
    return SpaceshipDto.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(null)
        .crewIds(null)
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static SpaceshipResponse buildSpaceshipResponse() {
    final PilotResponse pilotResponse = buildPilotResponse();

    return SpaceshipResponse.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(List.of(pilotResponse))
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static SpaceshipResponse buildAddSpaceshipResponse() {
    final PilotResponse addPilotResponse = buildAddPilotResponse();

    return SpaceshipResponse.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(List.of(addPilotResponse))
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static AddSpaceshipRequest buildAddSpaceshipRequest() {
    final AddPilotRequest addPilotRequest = buildAddPilotRequest();

    return AddSpaceshipRequest.builder()
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(List.of(addPilotRequest))
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static SpaceshipClientResponse buildSpaceshipClientResponse() {
    return SpaceshipClientResponse.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crewIds(List.of(ID))
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static SpaceshipClientResponse buildSpaceshipClientResponseWithNoCrewIds() {
    return SpaceshipClientResponse.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crewIds(Collections.emptyList())
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static AddSpaceshipClientRequest buildAddSpaceshipClientRequest() {
    return AddSpaceshipClientRequest.builder()
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .payload(SPACESHIP_PAYLOAD)
        .crewIds(List.of(ID))
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static PilotClientResponse buildPilotClientResponse() {
    return PilotClientResponse.builder()
        .id(ID)
        .name(PILOT_NAME)
        .species(PILOT_SPECIES)
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();
  }

  public static PilotClientResponse buildAddPilotClientResponse() {
    return PilotClientResponse.builder()
        .id(ID)
        .name(NEW_PILOT_NAME)
        .species(PILOT_SPECIES)
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();
  }

  public static PilotDto buildPilotDto() {
    return PilotDto.builder()
        .id(ID)
        .name(PILOT_NAME)
        .species(PILOT_SPECIES)
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();
  }

  public static PilotResponse buildPilotResponse() {
    return PilotResponse.builder()
        .id(ID)
        .name(PILOT_NAME)
        .species(PILOT_SPECIES)
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();
  }

  public static PilotDto buildAddPilotDto() {
    return PilotDto.builder()
        .id(null)
        .name(NEW_PILOT_NAME)
        .species(PILOT_SPECIES)
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();
  }

  public static PilotResponse buildAddPilotResponse() {
    return PilotResponse.builder()
        .id(ID)
        .name(NEW_PILOT_NAME)
        .species(PILOT_SPECIES)
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();
  }

  public static AddPilotRequest buildAddPilotRequest() {
    return AddPilotRequest.builder()
        .name(NEW_PILOT_NAME)
        .species(PILOT_SPECIES)
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();
  }

  public static AddPilotClientRequest buildAddPilotClientRequest() {
    return AddPilotClientRequest.builder()
        .name(NEW_PILOT_NAME)
        .species(PILOT_SPECIES)
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();
  }

  public static AddSpaceshipRequest buildInvalidAddSpaceshipRequest() {
    final AddPilotRequest invalidAddPilotRequest = AddPilotRequest.builder()
        .name(null)
        .species("")
        .profession(PILOT_PROFESSION)
        .weapons(PILOT_WEAPONS)
        .build();

    return AddSpaceshipRequest.builder()
        .name(null)
        .payload(20000)
        .classType(" ")
        .maxSpeed(20000)
        .armament(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"))
        .crew(List.of(invalidAddPilotRequest))
        .build();
  }

  public static ErrorResponse buildReqValidationFailedErrorResponse() {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final ErrorResponse errorResponse = ErrorResponse.builder()
        .status(status.value())
        .error(status.name())
        .message("Validation error. Check 'errors' field for details")
        .build();

    errorResponse.addValidationError("payload", "must be less than or equal to 10000");
    errorResponse.addValidationError("classType", "must not be blank");
    errorResponse.addValidationError("name", "must not be blank");
    errorResponse.addValidationError("armament", "size must be between 0 and 10");
    errorResponse.addValidationError("maxSpeed", "must be less than or equal to 10000");
    errorResponse.addValidationError("crew[0].species", "must not be blank");
    errorResponse.addValidationError("crew[0].name", "must not be blank");

    return errorResponse;
  }

  public static ErrorResponse buildNotFoundErrorResponse() {
    final HttpStatus status = HttpStatus.NOT_FOUND;

    return ErrorResponse.builder()
        .status(status.value())
        .error(status.name())
        .message(SPACESHIP_NOT_FOUND)
        .build();
  }

  public static ErrorResponse buildPilotExistsErrorResponse() {
    final HttpStatus status = HttpStatus.BAD_REQUEST;

    return ErrorResponse.builder()
        .status(status.value())
        .error(status.name())
        .message(PILOT_EXISTS)
        .build();
  }
}
