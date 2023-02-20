package com.project.port.common;

import com.project.port.dto.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Constant {

  private static final UUID ID = UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6");

  private static final UUID ID2 = UUID.fromString("a5eb9b26-3c8e-4f04-8d52-a43871fcc54e");

  private static final String SPACESHIP_NAME = "Tie Fighter";

  private static final String SPACESHIP_CLASS_TYPE = "Starfighter";

  private static final int SPACESHIP_CREW = 1;

  private static final int SPACESHIP_PAYLOAD = 10;

  private static final int SPACESHIP_MAX_SPEED = 1200;

  private static final List<String> SPACESHIP_ARMAMENT = List.of("Laser cannons");

  private static final List<UUID> SPACESHIP_CREW_LIST = Collections.emptyList();

  private static final String PILOT_NAME = "Darth Vader";

  private static final String PILOT_SPECIES = "Human";

  private static final String PILOT_PROFESSION = "Sith Lord";

  private static final List<String> PILOT_WEAPONS = List.of("Lightsaber");

  public static SpaceshipDto buildSpaceshipDto() {
    return SpaceshipDto.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(SPACESHIP_CREW)
        .crewIds(SPACESHIP_CREW_LIST)
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
        .armament(SPACESHIP_ARMAMENT)
        .build();
  }

  public static SpaceshipResponse buildSpaceshipResponse() {
    return SpaceshipResponse.builder()
        .id(ID)
        .name(SPACESHIP_NAME)
        .classType(SPACESHIP_CLASS_TYPE)
        .crew(SPACESHIP_CREW)
        .crewList(SPACESHIP_CREW_LIST)
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
        .crew(SPACESHIP_CREW)
        .crewList(SPACESHIP_CREW_LIST)
        .payload(SPACESHIP_PAYLOAD)
        .maxSpeed(SPACESHIP_MAX_SPEED)
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

  public static PilotClientRequest buildPilotClientRequest() {
    return PilotClientRequest.builder()
        .pilotUuids(List.of(ID, ID2))
        .build();
  }
}
