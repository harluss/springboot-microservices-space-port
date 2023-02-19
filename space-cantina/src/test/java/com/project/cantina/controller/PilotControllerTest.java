package com.project.cantina.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.cantina.dto.PilotDto;
import com.project.cantina.dto.PilotResponse;
import com.project.cantina.exception.ErrorResponse;
import com.project.cantina.exception.NotFoundException;
import com.project.cantina.mapper.PilotMapper;
import com.project.cantina.service.PilotService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.project.cantina.common.Constants.*;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

@WebMvcTest(PilotController.class)
class PilotControllerTest {

  private static final String TEST_API = "/api/pilots";

  private static final String TEST_API_WITH_ID = "/api/pilots/{id}";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PilotService pilotServiceMock;

  @MockBean
  private PilotMapper pilotMapperMock;

  private PilotResponse pilotResponse;

  private PilotDto pilotDto;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    pilotResponse = buildResponse();
    pilotDto = buildDto();
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
        .body(equalTo(toJsonString(List.of(pilotResponse))));

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
        .body(equalTo(toJsonString(Collections.emptyList())));

    verify(pilotServiceMock).getAll();
    verifyNoInteractions(pilotMapperMock);
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
        .body(equalTo(toJsonString(pilotResponse)));

    verify(pilotServiceMock).getById(reqId);
    verify(pilotMapperMock).dtoToResponse(pilotDto);
  }

  @Test
  void getPilotById_notFound() {
    final UUID reqId = pilotResponse.getId();
    final ErrorResponse expectedErrorResponse = buildNotFoundErrorResponse();
    when(pilotServiceMock.getById(reqId)).thenThrow(new NotFoundException(expectedErrorResponse.getMessage()));

    given()
        .when()
        .get(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(toJsonString(expectedErrorResponse)));

    verify(pilotServiceMock).getById(reqId);
    verifyNoInteractions(pilotMapperMock);
  }

  @SneakyThrows
  private String toJsonString(final Object object) {
    return objectMapper.writeValueAsString(object);
  }
}
