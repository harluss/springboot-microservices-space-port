package com.project.cantina.common;

import com.project.cantina.dto.PilotDto;
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

  public static UUID getRandomUUID() {
    return UUID.randomUUID();
  }

  public static ErrorResponse buildNotFoundErrorResponse() {
    final HttpStatus status = HttpStatus.NOT_FOUND;

    return ErrorResponse.builder()
        .status(status.value())
        .error(status.name())
        .message("Spaceship not found")
        .build();
  }
}
