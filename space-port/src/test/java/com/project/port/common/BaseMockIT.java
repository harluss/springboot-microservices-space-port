package com.project.port.common;

import com.project.port.dto.PilotClientRequest;
import com.project.port.dto.PilotClientResponse;
import com.project.port.dto.SpaceshipClientResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static com.project.port.common.Constant.buildPilotClientRequest;
import static com.project.port.common.Constant.buildPilotClientResponse;
import static com.project.port.common.Constant.buildSpaceshipClientResponse;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class BaseMockIT extends TestUtil {

  private final String SPACESHIPS_CLIENT_API = "/api/v1/spaceships";

  private static final String SPACESHIPS_CLIENT_API_WITH_ID_PATTERN = "/api/v1/spaceships/([a-zA-Z0-9/-]*)";

  private final String PILOTS_CLIENT_API = "/api/v1/pilots/crew";

  protected void stubForGetAllSpaceships() {
    final SpaceshipClientResponse spaceshipClientResponse = buildSpaceshipClientResponse();

    stubFor(get(SPACESHIPS_CLIENT_API)
        .willReturn(ok()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectToJsonString(List.of(spaceshipClientResponse)))));
  }

  protected void stubForGetSpaceshipById() {
    final SpaceshipClientResponse spaceshipClientResponse = buildSpaceshipClientResponse();

    stubFor(get(urlPathMatching(SPACESHIPS_CLIENT_API_WITH_ID_PATTERN))
        .willReturn(ok()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectToJsonString(spaceshipClientResponse))));
  }

  protected void stubForGetPilotsById() {
    final PilotClientResponse pilotClientResponse = buildPilotClientResponse();
    final PilotClientRequest pilotClientRequest = buildPilotClientRequest();

    stubFor(post(PILOTS_CLIENT_API)
        .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
        .withRequestBody(equalTo(objectToJsonString(pilotClientRequest)))
        .willReturn(ok()
            .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .withBody(objectToJsonString(List.of(pilotClientResponse)))));
  }
}
