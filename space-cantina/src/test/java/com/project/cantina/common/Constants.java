package com.project.cantina.common;

import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotIdsRequest;
import com.project.cantina.dto.PilotRequest;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.entity.PilotEntity;
import com.project.cantina.exception.ErrorResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@UtilityClass
public class Constants {

  private static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

  private static final UUID ID2 = UUID.fromString("a5eb9b26-3c8e-4f04-8d52-a43871fcc54e");

  private static final String NAME = "Darth Vader";

  private static final String SPECIES = "Human";

  private static final String PROFESSION = "Sith Lord";

  private static final List<String> WEAPONS = List.of("Lightsaber");

  public static PilotDto buildDto() {
    return PilotDto.builder()
        .id(ID)
        .name(NAME)
        .species(SPECIES)
        .profession(PROFESSION)
        .weapons(WEAPONS)
        .build();
  }

  public static PilotResponse buildResponse() {
    return PilotResponse.builder()
        .id(ID)
        .name(NAME)
        .species(SPECIES)
        .profession(PROFESSION)
        .weapons(WEAPONS)
        .build();
  }

  public static PilotEntity buildEntity() {
    return PilotEntity.builder()
        .id(ID)
        .name(NAME)
        .species(SPECIES)
        .profession(PROFESSION)
        .weapons(WEAPONS)
        .build();
  }

  public static PilotRequest buildRequest() {
    return PilotRequest.builder()
        .name(NAME)
        .species(SPECIES)
        .profession(PROFESSION)
        .weapons(WEAPONS)
        .build();
  }

  public static UUID getRandomUUID() {
    return UUID.randomUUID();
  }

  public static PilotRequest buildInvalidRequest() {
    return PilotRequest.builder()
        .name(" ")
        .species(null)
        .profession("")
        .weapons(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"))
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

    errorResponse.addValidationError("name", "must not be blank");
    errorResponse.addValidationError("species", "must not be blank");
    errorResponse.addValidationError("profession", "must not be blank");
    errorResponse.addValidationError("weapons", "size must be between 0 and 10");

    return errorResponse;
  }

  public static PilotIdsRequest buildPilotIdsRequest() {
    return PilotIdsRequest.builder()
        .pilotIds(List.of(ID, ID2))
        .build();
  }
}
