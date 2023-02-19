package com.project.hangar.controller;

import com.project.hangar.common.TestUtil;
import com.project.hangar.dto.SpaceshipDto;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.dto.SpaceshipResponse;
import com.project.hangar.exception.ErrorResponse;
import com.project.hangar.exception.NotFoundException;
import com.project.hangar.mapper.SpaceshipMapper;
import com.project.hangar.service.SpaceshipService;
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

import static com.project.hangar.common.Constants.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@WebMvcTest(SpaceshipController.class)
class SpaceshipControllerTest extends TestUtil {

  private static final String TEST_API = "/api/spaceships";

  private static final String TEST_API_WITH_ID = "/api/spaceships/{id}";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SpaceshipService spaceshipServiceMock;

  @MockBean
  private SpaceshipMapper spaceshipMapperMock;

  private SpaceshipDto spaceshipDto;

  private SpaceshipRequest spaceshipRequest;

  private SpaceshipResponse spaceshipResponse;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    spaceshipDto = buildDto();
    spaceshipRequest = buildRequest();
    spaceshipResponse = buildResponse();
  }

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(spaceshipMapperMock, spaceshipServiceMock);
  }

  @Test
  void getSpaceships() {
    when(spaceshipServiceMock.getAll()).thenReturn(List.of(spaceshipDto));
    when(spaceshipMapperMock.dtoToResponse(spaceshipDto)).thenReturn(spaceshipResponse);

    given()
        .when()
        .get(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(List.of(spaceshipResponse))));

    verify(spaceshipMapperMock).dtoToResponse(spaceshipDto);
    verify(spaceshipServiceMock).getAll();
  }

  @Test
  void getSpaceships_noShips() {
    when(spaceshipServiceMock.getAll()).thenReturn(Collections.emptyList());

    given()
        .when()
        .get(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(Collections.emptyList())));

    verify(spaceshipServiceMock).getAll();
    verifyNoInteractions(spaceshipMapperMock);
  }

  @Test
  void getSpaceshipById() {
    final UUID reqId = spaceshipResponse.getId();
    when(spaceshipServiceMock.getById(reqId)).thenReturn(spaceshipDto);
    when(spaceshipMapperMock.dtoToResponse(spaceshipDto)).thenReturn(spaceshipResponse);

    given()
        .when()
        .get(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(spaceshipResponse)));

    verify(spaceshipMapperMock).dtoToResponse(spaceshipDto);
    verify(spaceshipServiceMock).getById(reqId);
  }

  @Test
  void getSpaceshipById_notFound() {
    final UUID reqId = spaceshipResponse.getId();
    final ErrorResponse expectedErrorResponse = buildNotFoundErrorResponse();
    when(spaceshipServiceMock.getById(reqId)).thenThrow(new NotFoundException(expectedErrorResponse.getMessage()));

    given()
        .when()
        .get(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedErrorResponse)));

    verify(spaceshipServiceMock).getById(reqId);
    verifyNoInteractions(spaceshipMapperMock);
  }

  @Test
  void addSpaceship() {
    when(spaceshipMapperMock.requestToDto(spaceshipRequest)).thenReturn(spaceshipDto);
    when(spaceshipServiceMock.add(spaceshipDto)).thenReturn(spaceshipDto);
    when(spaceshipMapperMock.dtoToResponse(spaceshipDto)).thenReturn(spaceshipResponse);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(spaceshipRequest))
        .when()
        .post(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(spaceshipResponse)));

    verify(spaceshipMapperMock).requestToDto(spaceshipRequest);
    verify(spaceshipMapperMock).dtoToResponse(spaceshipDto);
    verify(spaceshipServiceMock).add(spaceshipDto);
  }

  @Test
  void addSpaceship_invalidRequestBody() {
    final SpaceshipRequest invalidSpaceshipRequest = buildInvalidRequest();
    final ErrorResponse expectedErrorResponse = buildReqValidationFailedErrorResponse();

    final String responseBody = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(invalidSpaceshipRequest))
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
    verifyNoInteractions(spaceshipMapperMock);
    verifyNoInteractions(spaceshipServiceMock);
  }

  @Test
  void updateSpaceshipById() {
    final UUID reqId = spaceshipResponse.getId();
    when(spaceshipMapperMock.requestToDto(spaceshipRequest)).thenReturn(spaceshipDto);
    when(spaceshipServiceMock.updateById(spaceshipDto, reqId)).thenReturn(spaceshipDto);
    when(spaceshipMapperMock.dtoToResponse(spaceshipDto)).thenReturn(spaceshipResponse);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(spaceshipRequest))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(spaceshipResponse)));

    verify(spaceshipMapperMock).requestToDto(spaceshipRequest);
    verify(spaceshipMapperMock).dtoToResponse(spaceshipDto);
    verify(spaceshipServiceMock).updateById(spaceshipDto, reqId);
  }

  @Test
  void updateSpaceshipById_notFound() {
    final UUID reqId = spaceshipResponse.getId();
    final ErrorResponse expectedErrorResponse = buildNotFoundErrorResponse();
    when(spaceshipMapperMock.requestToDto(spaceshipRequest)).thenReturn(spaceshipDto);
    doThrow(new NotFoundException(expectedErrorResponse.getMessage()))
        .when(spaceshipServiceMock).updateById(spaceshipDto, reqId);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(spaceshipRequest))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedErrorResponse)));

    verify(spaceshipMapperMock).requestToDto(spaceshipRequest);
    verify(spaceshipServiceMock).updateById(spaceshipDto, reqId);
  }

  @Test
  void updateSpaceshipById_invalidRequestBody() {
    final UUID reqId = spaceshipResponse.getId();
    final SpaceshipRequest invalidPilotRequest = buildInvalidRequest();
    final ErrorResponse expectedErrorResponse = buildReqValidationFailedErrorResponse();

    final String responseBody = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(invalidPilotRequest))
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
    verifyNoInteractions(spaceshipMapperMock);
    verifyNoInteractions(spaceshipServiceMock);
  }

  @Test
  void deleteSpaceshipById() {
    final UUID reqId = spaceshipResponse.getId();

    given()
        .when()
        .delete(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NO_CONTENT.value());

    verify(spaceshipServiceMock).deleteById(reqId);
  }

  @Test
  void deleteSpaceshipById_notFound() {
    final UUID reqId = spaceshipResponse.getId();
    final ErrorResponse expectedErrorResponse = buildNotFoundErrorResponse();
    doThrow(new NotFoundException(expectedErrorResponse.getMessage())).when(spaceshipServiceMock).deleteById(reqId);

    given()
        .when()
        .delete(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedErrorResponse)));

    verify(spaceshipServiceMock).deleteById(reqId);
  }
}
