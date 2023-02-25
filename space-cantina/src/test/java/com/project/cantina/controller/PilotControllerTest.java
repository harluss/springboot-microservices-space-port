package com.project.cantina.controller;

import com.project.cantina.common.TestUtil;
import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotIdsRequest;
import com.project.cantina.dto.PilotRequest;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.dto.PilotUpdateRequest;
import com.project.cantina.exception.ErrorResponse;
import com.project.cantina.exception.NotFoundException;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.service.PilotService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.project.cantina.common.Constants.buildDto;
import static com.project.cantina.common.Constants.buildInvalidRequest;
import static com.project.cantina.common.Constants.buildInvalidUpdateRequest;
import static com.project.cantina.common.Constants.buildNotFoundErrorResponse;
import static com.project.cantina.common.Constants.buildPilotIdsRequest;
import static com.project.cantina.common.Constants.buildReqValidationFailedErrorResponse;
import static com.project.cantina.common.Constants.buildRequest;
import static com.project.cantina.common.Constants.buildResponse;
import static com.project.cantina.common.Constants.buildUpdateRequest;
import static com.project.cantina.common.Constants.getRandomUUID;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@WebMvcTest(PilotController.class)
class PilotControllerTest extends TestUtil {

  private static final String TEST_API = "/api/v1/pilots";

  private static final String TEST_API_WITH_ID = "/api/v1/pilots/{id}";

  private static final String TEST_API_CREW = "/api/v1/pilots/crew";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PilotService pilotServiceMock;

  @MockBean
  private PilotMapper pilotMapperMock;

  private PilotResponse pilotResponse;

  private PilotDto pilotDto;

  private PilotRequest pilotRequest;

  private PilotUpdateRequest pilotUpdateRequest;

  private PilotIdsRequest pilotIdsRequest;

  private ErrorResponse expectedErrorResponse;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    pilotResponse = buildResponse();
    pilotDto = buildDto();
    pilotRequest = buildRequest();
    pilotUpdateRequest = buildUpdateRequest();
    pilotIdsRequest = buildPilotIdsRequest();
    expectedErrorResponse = buildNotFoundErrorResponse();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(pilotServiceMock, pilotMapperMock);
  }

  @Test
  void getPilots() {
    when(pilotServiceMock.getAll()).thenReturn(List.of(pilotDto));
    when(pilotMapperMock.dtoToResponse(pilotDto)).thenReturn(pilotResponse);

    given()
        .when()
        .get(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(List.of(pilotResponse))));

    verify(pilotServiceMock).getAll();
    verify(pilotMapperMock).dtoToResponse(pilotDto);
  }

  @Test
  void getPilots_noPilots() {
    when(pilotServiceMock.getAll()).thenReturn(Collections.emptyList());

    given()
        .when()
        .get(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(Collections.emptyList())));

    verify(pilotServiceMock).getAll();
    verifyNoInteractions(pilotMapperMock);
  }

  @Test
  void getPilotsByIds() {
    when(pilotServiceMock.getAllByIds(pilotIdsRequest.getPilotIds())).thenReturn(List.of(pilotDto));
    when(pilotMapperMock.dtoToResponse(pilotDto)).thenReturn(pilotResponse);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(pilotIdsRequest))
        .when()
        .post(TEST_API_CREW)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(List.of(pilotDto))));

    verify(pilotServiceMock).getAllByIds(pilotIdsRequest.getPilotIds());
    verify(pilotMapperMock).dtoToResponse(pilotDto);
  }

  @Test
  void getPilotById() {
    final UUID reqId = pilotResponse.getId();
    when(pilotServiceMock.getById(reqId)).thenReturn(pilotDto);
    when(pilotMapperMock.dtoToResponse(pilotDto)).thenReturn(pilotResponse);

    given()
        .when()
        .get(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(pilotResponse)));

    verify(pilotServiceMock).getById(reqId);
    verify(pilotMapperMock).dtoToResponse(pilotDto);
  }

  @Test
  void getPilotById_notFound() {
    final UUID reqId = pilotResponse.getId();
    when(pilotServiceMock.getById(reqId)).thenThrow(new NotFoundException(expectedErrorResponse.getMessage()));

    given()
        .when()
        .get(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedErrorResponse)));

    verify(pilotServiceMock).getById(reqId);
    verifyNoInteractions(pilotMapperMock);
  }

  @Test
  void addPilot() {
    when(pilotMapperMock.requestToDto(pilotRequest)).thenReturn(pilotDto);
    when(pilotServiceMock.add(pilotDto)).thenReturn(pilotDto);
    when(pilotMapperMock.dtoToResponse(pilotDto)).thenReturn(pilotResponse);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(pilotRequest))
        .when()
        .post(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(pilotResponse)));

    verify(pilotMapperMock).requestToDto(pilotRequest);
    verify(pilotMapperMock).dtoToResponse(pilotDto);
    verify(pilotServiceMock).add(pilotDto);
  }

  @Test
  void addPilot_invalidRequestBody() {
    final PilotRequest invalidPilotRequest = buildInvalidRequest();
    final ErrorResponse expectedErrorResponse = buildReqValidationFailedErrorResponse();

    final String responseBody = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(invalidPilotRequest))
        .when()
        .post(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .extract()
        .body()
        .asString();

    final ErrorResponse actualErrorResponse = jsonStringToObject(responseBody, ErrorResponse.class);

    assertThat(actualErrorResponse).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedErrorResponse);
    verifyNoInteractions(pilotMapperMock);
    verifyNoInteractions(pilotServiceMock);
  }

  @Test
  void updatePilotById() {
    final UUID reqId = pilotResponse.getId();
    when(pilotMapperMock.updateRequestToDto(pilotUpdateRequest)).thenReturn(pilotDto);
    when(pilotServiceMock.updateById(pilotDto, reqId)).thenReturn(pilotDto);
    when(pilotMapperMock.dtoToResponse(pilotDto)).thenReturn(pilotResponse);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(pilotUpdateRequest))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(pilotResponse)));

    verify(pilotMapperMock).updateRequestToDto(pilotUpdateRequest);
    verify(pilotMapperMock).dtoToResponse(pilotDto);
    verify(pilotServiceMock).updateById(pilotDto, reqId);
  }

  @Test
  void updatePilotById_notFound() {
    final UUID reqId = getRandomUUID();
    final ErrorResponse expectedErrorResponse = buildNotFoundErrorResponse();
    when(pilotMapperMock.updateRequestToDto(pilotUpdateRequest)).thenReturn(pilotDto);
    doThrow(new NotFoundException(expectedErrorResponse.getMessage()))
        .when(pilotServiceMock).updateById(pilotDto, reqId);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(pilotUpdateRequest))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedErrorResponse)));

    verify(pilotMapperMock).updateRequestToDto(pilotUpdateRequest);
    verify(pilotServiceMock).updateById(pilotDto, reqId);
  }

  @Test
  void updatePilotById_invalidRequestBody() {
    final UUID reqId = pilotResponse.getId();
    final PilotUpdateRequest invalidPilotUpdateRequest = buildInvalidUpdateRequest();
    final ErrorResponse expectedErrorResponse = buildReqValidationFailedErrorResponse();

    final String responseBody = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(invalidPilotUpdateRequest))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .extract()
        .body()
        .asString();

    final ErrorResponse actualErrorResponse = jsonStringToObject(responseBody, ErrorResponse.class);

    assertThat(actualErrorResponse).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedErrorResponse);
    verifyNoInteractions(pilotMapperMock);
    verifyNoInteractions(pilotServiceMock);
  }

  @Test
  void deletePilotById() {
    final UUID reqId = pilotResponse.getId();

    given()
        .when()
        .delete(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NO_CONTENT.value());

    verify(pilotServiceMock).deleteById(reqId);
  }

  @Test
  void deletePilotById_notFound() {
    final UUID reqId = pilotResponse.getId();
    final ErrorResponse expectedErrorResponse = buildNotFoundErrorResponse();
    doThrow(new NotFoundException(expectedErrorResponse.getMessage())).when(pilotServiceMock).deleteById(reqId);

    given()
        .when()
        .delete(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedErrorResponse)));

    verify(pilotServiceMock).deleteById(reqId);
  }
}
