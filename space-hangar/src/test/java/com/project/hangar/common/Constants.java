package com.project.hangar.common;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;
import com.project.hangar.exception.ErrorResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class Constants {

  private static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

  private static final String NAME = "Tie Fighter";

  private static final String CLASS_TYPE = "Starfighter";

  private static final int PAYLOAD = 10;

  private static final int MAX_SPEED = 1200;

  private static final List<String> ARMAMENT = List.of("Laser cannons");

  private static final List<UUID> CREW = Collections.emptyList();

  public static SpaceshipDto buildDto() {
    return SpaceshipDto.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .maxSpeed(MAX_SPEED)
        .armament(ARMAMENT)
        .build();
  }

  public static SpaceshipResponse buildResponse() {
    return SpaceshipResponse.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .maxSpeed(MAX_SPEED)
        .armament(ARMAMENT)
        .build();
  }

  public static SpaceshipEntity buildEntity() {
    return SpaceshipEntity.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .maxSpeed(MAX_SPEED)
        .armament(ARMAMENT)
        .build();
  }

  public static SpaceshipRequest buildRequest() {
    return SpaceshipRequest.builder()
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .maxSpeed(MAX_SPEED)
        .armament(ARMAMENT)
        .build();
  }

  public static UUID getRandomUUID() {
    return UUID.randomUUID();
  }

  public static SpaceshipRequest buildInvalidRequest() {
    return SpaceshipRequest.builder()
        .name(null)
        .payload(20000)
        .classType(" ")
        .maxSpeed(20000)
        .armament(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"))
        .crew(List.of(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()))
        .build();
  }

  public static ErrorResponse buildNotFoundErrorResponse() {
    final HttpStatus status = HttpStatus.NOT_FOUND;

    return ErrorResponse.builder()
        .status(status.value())
        .error(status.name())
        .message("Spaceship not found")
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
    errorResponse.addValidationError("crew", "size must be between 0 and 5");
    errorResponse.addValidationError("armament", "size must be between 0 and 10");
    errorResponse.addValidationError("maxSpeed", "must be less than or equal to 10000");

    return errorResponse;
  }
}
