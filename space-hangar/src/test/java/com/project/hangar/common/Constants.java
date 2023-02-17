package com.project.hangar.common;

import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.entity.SpaceshipEntity;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class Constants {

  public static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

  static final String NAME = "Tie Fighter";

  static final String CLASS_TYPE = "Starfighter";

  static final int CREW = 1;

  static final int PAYLOAD = 10;

  public static SpaceshipDto buildDto() {
    return SpaceshipDto.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .build();
  }

  public static SpaceshipResponse buildResponse() {
    return SpaceshipResponse.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .build();
  }

  public static SpaceshipEntity buildEntity() {
    return SpaceshipEntity.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .build();
  }

  public static SpaceshipRequest buildRequest() {
    return SpaceshipRequest.builder()
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .payload(PAYLOAD)
        .build();
  }

  public static UUID getRandomUUID() {
    return UUID.randomUUID();
  }
}
