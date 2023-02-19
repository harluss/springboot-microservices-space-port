package com.project.port.common;

import com.project.port.dto.SpaceshipClientResponse;
import com.project.port.dto.SpaceshipDto;
import com.project.port.dto.SpaceshipResponse;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Constant {

  private static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

  private static final String NAME = "Tie Fighter";

  private static final String CLASS_TYPE = "Starfighter";

  private static final int CREW = 1;

  private static final int PAYLOAD = 10;

  private static final int MAX_SPEED = 1200;

  private static final List<String> ARMAMENT = List.of("Laser cannons");

  private static final List<UUID> CREW_LIST = Collections.emptyList();

  public static SpaceshipDto buildDto() {
    return SpaceshipDto.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .crewList(CREW_LIST)
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
        .crewList(CREW_LIST)
        .payload(PAYLOAD)
        .maxSpeed(MAX_SPEED)
        .armament(ARMAMENT)
        .build();
  }

  public static SpaceshipClientResponse buildClientResponse() {
    return SpaceshipClientResponse.builder()
        .id(ID)
        .name(NAME)
        .classType(CLASS_TYPE)
        .crew(CREW)
        .crewList(CREW_LIST)
        .payload(PAYLOAD)
        .maxSpeed(MAX_SPEED)
        .armament(ARMAMENT)
        .build();
  }
}
