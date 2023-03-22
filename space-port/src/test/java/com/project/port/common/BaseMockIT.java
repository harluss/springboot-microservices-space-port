package com.project.port.common;

import static com.github.tomakehurst.wiremock.client.WireMock.created;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.noContent;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.project.port.common.Constant.buildAddPilotClientResponse;
import static com.project.port.common.Constant.buildAddPilotRequest;
import static com.project.port.common.Constant.buildAddSpaceshipClientRequest;
import static com.project.port.common.Constant.buildPilotClientResponse;
import static com.project.port.common.Constant.buildSpaceshipClientResponse;
import static com.project.port.common.Constant.buildUpdateSpaceshipClientRequest;
import static com.project.port.common.Constant.buildUpdateSpaceshipClientResponse;

import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import com.project.port.dto.pilot.AddPilotRequest;
import com.project.port.dto.pilot.PilotClientResponse;
import com.project.port.dto.spaceship.AddSpaceshipClientRequest;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import com.project.port.dto.spaceship.UpdateSpaceshipClientRequest;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class BaseMockIT extends TestUtil {

  private static final String SPACESHIPS_CLIENT_API = "/api/v1/spaceships";

  private static final String SPACESHIPS_CLIENT_API_WITH_ID_PATTERN = "/api/v1/spaceships/([a-zA-Z0-9/-]*)";

  private static final String PILOTS_CLIENT_API = "/api/v1/pilots";

  private static final String PILOTS_CLIENT_API_WITH_ID_PATTERN = "/api/v1/pilots/([a-zA-Z0-9/-]*)";

  protected void stubForGetAllSpaceships() {
    final SpaceshipClientResponse spaceshipClientResponse = buildSpaceshipClientResponse();

    stubFor(get(SPACESHIPS_CLIENT_API).willReturn(ok()
        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .withBody(objectToJsonString(List.of(spaceshipClientResponse)))));
  }

  protected void stubForGetSpaceshipById() {
    final SpaceshipClientResponse spaceshipClientResponse = buildSpaceshipClientResponse();

    stubFor(get(urlPathMatching(SPACESHIPS_CLIENT_API_WITH_ID_PATTERN)).willReturn(ok()
        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .withBody(objectToJsonString(spaceshipClientResponse))));
  }

  protected void stubForAddSpaceship() {
    final SpaceshipClientResponse spaceshipClientResponse = buildSpaceshipClientResponse();
    final AddSpaceshipClientRequest addSpaceshipClientRequest = buildAddSpaceshipClientRequest();

    stubFor(post(SPACESHIPS_CLIENT_API)
        .withRequestBody(equalTo(objectToJsonString(addSpaceshipClientRequest)))
        .willReturn(created()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectToJsonString(spaceshipClientResponse))));
  }

  protected void stubForUpdateSpaceship() {
    final UpdateSpaceshipClientRequest updateSpaceshipClientRequest = buildUpdateSpaceshipClientRequest();
    final SpaceshipClientResponse updateSpaceshipClientResponse = buildUpdateSpaceshipClientResponse();

    stubFor(put(urlPathMatching(SPACESHIPS_CLIENT_API_WITH_ID_PATTERN))
        .withRequestBody(equalTo(objectToJsonString(updateSpaceshipClientRequest)))
        .willReturn(ok()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectToJsonString(updateSpaceshipClientResponse))));
  }

  protected void stubForGetAllPilots() {
    final PilotClientResponse pilotClientResponse = buildPilotClientResponse();

    stubFor(get(PILOTS_CLIENT_API).willReturn(ok()
        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .withBody(objectToJsonString(List.of(pilotClientResponse)))));
  }

  protected void stubForAddPilot() {
    final AddPilotRequest addPilotRequest = buildAddPilotRequest();
    final PilotClientResponse addPilotClientResponse = buildAddPilotClientResponse();

    stubFor(post(PILOTS_CLIENT_API)
        .withRequestBody(equalTo(objectToJsonString(addPilotRequest)))
        .willReturn(created()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectToJsonString(addPilotClientResponse))));
  }

  protected void stubForDeleteSpaceship() {
    stubFor(delete(urlPathMatching(SPACESHIPS_CLIENT_API_WITH_ID_PATTERN)).willReturn(noContent()));
  }

  protected void stubForDeletePilot() {
    stubFor(delete(urlPathMatching(PILOTS_CLIENT_API_WITH_ID_PATTERN)).willReturn(noContent()));
  }
}
