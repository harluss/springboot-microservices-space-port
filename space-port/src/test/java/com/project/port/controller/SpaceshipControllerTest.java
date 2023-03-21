package com.project.port.controller;

import static com.project.port.common.Constant.buildAddSpaceshipRequest;
import static com.project.port.common.Constant.buildAddValidationFailedErrorResponse;
import static com.project.port.common.Constant.buildInvalidAddSpaceshipRequest;
import static com.project.port.common.Constant.buildInvalidUpdateSpaceshipRequest;
import static com.project.port.common.Constant.buildNotFoundErrorResponse;
import static com.project.port.common.Constant.buildPilotExistsErrorResponse;
import static com.project.port.common.Constant.buildSpaceshipDto;
import static com.project.port.common.Constant.buildSpaceshipResponse;
import static com.project.port.common.Constant.buildUpdateSpaceshipDtoWithNoId;
import static com.project.port.common.Constant.buildUpdateSpaceshipRequest;
import static com.project.port.common.Constant.buildUpdateSpaceshipResponse;
import static com.project.port.common.Constant.buildUpdateValidationFailedErrorResponse;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.project.port.common.TestUtil;
import com.project.port.dto.spaceship.AddSpaceshipRequest;
import com.project.port.dto.spaceship.SpaceshipDto;
import com.project.port.dto.spaceship.SpaceshipResponse;
import com.project.port.dto.spaceship.UpdateSpaceshipRequest;
import com.project.port.dto.spaceship.UpdateSpaceshipResponse;
import com.project.port.exception.ErrorResponse;
import com.project.port.exception.NotFoundException;
import com.project.port.exception.PilotExistsException;
import com.project.port.mapper.SpaceshipMapper;
import com.project.port.service.SpaceshipService;

@ActiveProfiles("test")
@WebMvcTest(SpaceshipController.class)
@MockitoSettings(strictness = Strictness.WARN)
class SpaceshipControllerTest extends TestUtil {

  private static final String TEST_API = "/api/v1/spaceships";

  private static final String TEST_API_WITH_ID = "/api/v1/spaceships/{id}";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SpaceshipService spaceshipServiceMock;

  @MockBean
  private SpaceshipMapper spaceshipMapperMock;

  private SpaceshipDto spaceshipDto;

  private SpaceshipResponse spaceshipResponse;

  private AddSpaceshipRequest addSpaceshipRequest;

  private AddSpaceshipRequest invalidAddSpaceshipRequest;

  private UpdateSpaceshipRequest updateSpaceshipRequest;

  private UpdateSpaceshipResponse updateSpaceshipResponse;

  private SpaceshipDto updateSpaceshipDto;

  private ErrorResponse expectedNotFoundErrorResponse;

  private ErrorResponse expectedAddValidationErrorResponse;

  private UpdateSpaceshipResponse invalidUpdateSpaceshipRequest;

  private ErrorResponse expectedUpdateValidationErrorResponse;

  private ErrorResponse expectedPilotExistsErrorResponse;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    spaceshipDto = buildSpaceshipDto();
    spaceshipResponse = buildSpaceshipResponse();
    expectedNotFoundErrorResponse = buildNotFoundErrorResponse();
    addSpaceshipRequest = buildAddSpaceshipRequest();
    invalidAddSpaceshipRequest = buildInvalidAddSpaceshipRequest();
    expectedAddValidationErrorResponse = buildAddValidationFailedErrorResponse();
    expectedPilotExistsErrorResponse = buildPilotExistsErrorResponse();
    updateSpaceshipRequest = buildUpdateSpaceshipRequest();
    updateSpaceshipDto = buildUpdateSpaceshipDtoWithNoId();
    updateSpaceshipResponse = buildUpdateSpaceshipResponse();
    invalidUpdateSpaceshipRequest = buildInvalidUpdateSpaceshipRequest();
    expectedUpdateValidationErrorResponse = buildUpdateValidationFailedErrorResponse();
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
        .log()
        .body()
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
        .log()
        .body()
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
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(spaceshipResponse)));

    verify(spaceshipMapperMock).dtoToResponse(spaceshipDto);
    verify(spaceshipServiceMock).getById(reqId);
  }

  @Test
  void getSpaceshipById_notFound() {
    final UUID reqId = spaceshipResponse.getId();
    when(spaceshipServiceMock.getById(reqId)).thenThrow(
        new NotFoundException(expectedNotFoundErrorResponse.getMessage()));

    given()
        .when()
        .get(TEST_API_WITH_ID, reqId)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedNotFoundErrorResponse)));

    verify(spaceshipServiceMock).getById(reqId);
    verifyNoInteractions(spaceshipMapperMock);
  }

  @Test
  void addSpaceship() {
    when(spaceshipMapperMock.addRequestToDto(addSpaceshipRequest)).thenReturn(spaceshipDto);
    when(spaceshipServiceMock.add(spaceshipDto)).thenReturn(spaceshipDto);
    when(spaceshipMapperMock.dtoToResponse(spaceshipDto)).thenReturn(spaceshipResponse);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(addSpaceshipRequest))
        .when()
        .post(TEST_API)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.CREATED.value())
        .header(HttpHeaders.LOCATION, endsWith(TEST_API + "/" + spaceshipResponse.getId()))
        .body(equalTo(objectToJsonString(spaceshipResponse)));

    verify(spaceshipMapperMock).addRequestToDto(addSpaceshipRequest);
    verify(spaceshipMapperMock).dtoToResponse(spaceshipDto);
    verify(spaceshipServiceMock).add(spaceshipDto);
  }

  @Test
  void addSpaceship_invalidRequestBody() {

    final String responseBody = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(invalidAddSpaceshipRequest))
        .when()
        .post(TEST_API)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .extract()
        .body()
        .asString();

    final ErrorResponse actualErrorResponse = jsonStringToObject(responseBody, ErrorResponse.class);

    assertThat(actualErrorResponse)
        .usingRecursiveComparison()
        .ignoringCollectionOrder()
        .isEqualTo(expectedAddValidationErrorResponse);
    verifyNoInteractions(spaceshipMapperMock);
    verifyNoInteractions(spaceshipServiceMock);
  }

  @Test
  void addSpaceship_pilotsExist() {
    when(spaceshipMapperMock.addRequestToDto(addSpaceshipRequest)).thenReturn(spaceshipDto);
    when(spaceshipServiceMock.add(spaceshipDto)).thenThrow(
        new PilotExistsException(expectedPilotExistsErrorResponse.getMessage()));

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(addSpaceshipRequest))
        .when()
        .post(TEST_API)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .body(equalTo(objectToJsonString(expectedPilotExistsErrorResponse)));

    verify(spaceshipMapperMock).addRequestToDto(addSpaceshipRequest);
    verify(spaceshipServiceMock).add(spaceshipDto);
    verify(spaceshipMapperMock, times(0)).dtoToResponse(any());
  }

  @Test
  void updateSpaceshipById() {
    final UUID reqId = spaceshipResponse.getId();
    when(spaceshipMapperMock.updateRequestToDto(updateSpaceshipRequest)).thenReturn(updateSpaceshipDto);
    when(spaceshipServiceMock.updateById(updateSpaceshipDto, reqId)).thenReturn(updateSpaceshipDto);
    when(spaceshipMapperMock.dtoToUpdateResponse(updateSpaceshipDto)).thenReturn(updateSpaceshipResponse);

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(updateSpaceshipDto))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body(equalTo(objectToJsonString(updateSpaceshipResponse)));

    verify(spaceshipMapperMock).updateRequestToDto(updateSpaceshipRequest);
    verify(spaceshipServiceMock).updateById(updateSpaceshipDto, reqId);
    verify(spaceshipMapperMock).dtoToUpdateResponse(updateSpaceshipDto);
  }

  @Test
  void updateSpaceshipById_noFound() {
    final UUID reqId = spaceshipResponse.getId();
    when(spaceshipMapperMock.updateRequestToDto(updateSpaceshipRequest)).thenReturn(updateSpaceshipDto);
    when(spaceshipServiceMock.updateById(updateSpaceshipDto, reqId)).thenThrow(
        new NotFoundException(expectedNotFoundErrorResponse.getMessage()));

    given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(updateSpaceshipDto))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedNotFoundErrorResponse)));

    verify(spaceshipMapperMock).updateRequestToDto(updateSpaceshipRequest);
    verify(spaceshipServiceMock).updateById(updateSpaceshipDto, reqId);
    verify(spaceshipMapperMock, times(0)).dtoToUpdateResponse(updateSpaceshipDto);
  }

  @Test
  void updateSpaceshipById_invalidRequestBody() {
    final UUID reqId = spaceshipResponse.getId();

    final String responseBody = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(invalidUpdateSpaceshipRequest))
        .when()
        .put(TEST_API_WITH_ID, reqId)
        .then()
        .log()
        .body()
        .assertThat()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .extract()
        .body()
        .asString();

    final ErrorResponse actualErrorResponse = jsonStringToObject(responseBody, ErrorResponse.class);

    assertThat(actualErrorResponse)
        .usingRecursiveComparison()
        .ignoringCollectionOrder()
        .isEqualTo(expectedUpdateValidationErrorResponse);
    verifyNoInteractions(spaceshipMapperMock);
    verifyNoInteractions(spaceshipServiceMock);
  }
}
