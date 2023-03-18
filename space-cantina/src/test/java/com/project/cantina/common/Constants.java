package com.project.cantina.common;

import com.project.cantina.dto.AddPilotRequest;
import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.dto.UpdatePilotRequest;
import com.project.cantina.entity.PilotEntity;
import com.project.cantina.exception.ErrorResponse;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class Constants {

  private static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

  private static final String NAME = "Darth Vader";

  private static final String SPECIES = "Human";

  private static final String PROFESSION = "Sith Lord";

  private static final String UPDATED_PROFESSION = "Supreme Commander";

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

  public static AddPilotRequest buildRequest() {
    return AddPilotRequest.builder()
        .name(NAME)
        .species(SPECIES)
        .profession(PROFESSION)
        .weapons(WEAPONS)
        .build();
  }

  public static UpdatePilotRequest buildUpdateRequest() {
    return UpdatePilotRequest.builder()
        .species(SPECIES)
        .profession(UPDATED_PROFESSION)
        .weapons(Collections.emptyList())
        .build();
  }

  public static UUID getRandomUUID() {
    return UUID.randomUUID();
  }

  public static AddPilotRequest buildInvalidRequest() {
    return AddPilotRequest.builder()
        .name(NAME)
        .species(null)
        .profession("")
        .weapons(List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"))
        .build();
  }

  public static UpdatePilotRequest buildInvalidUpdateRequest() {
    return UpdatePilotRequest.builder()
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
        .message("Pilot not found")
        .build();
  }

  public static ErrorResponse buildAlreadyExistsErrorResponse() {
    final HttpStatus status = HttpStatus.BAD_REQUEST;

    return ErrorResponse.builder()
        .status(status.value())
        .error(status.name())
        .message("Pilot already exists")
        .build();
  }

  public static ErrorResponse buildReqValidationFailedErrorResponse() {
    final HttpStatus status = HttpStatus.BAD_REQUEST;
    final ErrorResponse errorResponse = ErrorResponse.builder()
        .status(status.value())
        .error(status.name())
        .message("Validation error. Check 'errors' field for details")
        .build();

    errorResponse.addValidationError("species", "must not be blank");
    errorResponse.addValidationError("profession", "must not be blank");
    errorResponse.addValidationError("weapons", "size must be between 0 and 10");

    return errorResponse;
  }
}
