package com.project.port.controller;

import com.project.port.common.TestUtil;
import com.project.port.dto.SpaceshipDto;
import com.project.port.dto.SpaceshipResponse;
import com.project.port.exception.ErrorResponse;
import com.project.port.exception.NotFoundException;
import com.project.port.mapper.SpaceshipMapper;
import com.project.port.service.SpaceshipService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.project.port.common.Constant.buildNotFoundErrorResponse;
import static com.project.port.common.Constant.buildSpaceshipDto;
import static com.project.port.common.Constant.buildSpaceshipResponse;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

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

  private ErrorResponse expectedNotFoundErrorResponse;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    spaceshipDto = buildSpaceshipDto();
    spaceshipResponse = buildSpaceshipResponse();
    expectedNotFoundErrorResponse = buildNotFoundErrorResponse();
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
    when(spaceshipServiceMock.getById(reqId)).thenThrow(new NotFoundException(expectedNotFoundErrorResponse.getMessage()));

    given()
        .when()
        .get(TEST_API_WITH_ID, reqId)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NOT_FOUND.value())
        .body(equalTo(objectToJsonString(expectedNotFoundErrorResponse)));

    verify(spaceshipServiceMock).getById(reqId);
    verifyNoInteractions(spaceshipMapperMock);
  }
}
