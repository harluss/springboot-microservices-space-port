package com.project.cantina.integration;

import com.project.cantina.common.BaseIT;
import com.project.cantina.dto.PilotRequest;
import com.project.cantina.dto.PilotUpdateRequest;
import com.project.cantina.entity.PilotEntity;
import com.project.cantina.repository.PilotRepository;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.project.cantina.common.Constants.buildRequest;
import static com.project.cantina.common.Constants.buildUpdateRequest;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PilotIT extends BaseIT {

  private static final String TEST_API = "/api/v1/pilots";

  private static final String TEST_API_WITH_ID = "/api/v1/pilots/{id}";

  private List<PilotEntity> pilotsBefore;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PilotRepository pilotRepository;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    pilotsBefore = pilotRepository.findAll();
  }

  @Test
  void getPilots_happyPath() {

    final List<PilotEntity> actual = given()
        .when()
        .get(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .body()
        .as(new TypeRef<>() {
        });

    assertThat(actual).hasSize(pilotsBefore.size());
  }

  @Test
  void getPilotById_happyPath() {
    final PilotEntity expected = pilotsBefore.get(0);

    final PilotEntity actual = given()
        .when()
        .get(TEST_API_WITH_ID, expected.getId())
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .body()
        .as(new TypeRef<>() {
        });

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void addPilot_happyPath() {
    final PilotRequest request = buildRequest();

    final PilotEntity actual = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(request))
        .when()
        .post(TEST_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.CREATED.value())
        .header(HttpHeaders.LOCATION, notNullValue())
        .extract()
        .body()
        .as(new TypeRef<>() {
        });

    final List<PilotEntity> spaceshipsAfter = pilotRepository.findAll();

    assertThat(pilotsBefore).doesNotContain(actual);
    assertThat(spaceshipsAfter)
        .contains(actual)
        .hasSize(pilotsBefore.size() + 1);
    assertThat(request).usingRecursiveComparison().isEqualTo(actual);
  }

  @Test
  void updatePilotById_happyPath() {
    final PilotUpdateRequest request = buildUpdateRequest();
    final PilotEntity toBeUpdated = pilotsBefore.get(0);

    final PilotEntity actual = given()
        .contentType(MediaType.APPLICATION_JSON)
        .body(objectToJsonString(request))
        .when()
        .put(TEST_API_WITH_ID, toBeUpdated.getId())
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .body()
        .as(new TypeRef<>() {
        });

    final List<PilotEntity> spaceshipsAfter = pilotRepository.findAll();

    assertThat(toBeUpdated.getId()).isEqualTo(actual.getId());
    assertThat(pilotsBefore)
        .contains(toBeUpdated)
        .doesNotContain(actual);
    assertThat(spaceshipsAfter)
        .doesNotContain(toBeUpdated)
        .contains(actual);
  }

  @Test
  void deletePilotById_happyPath() {
    final PilotEntity toBeDeleted = pilotsBefore.get(0);

    given()
        .when()
        .delete(TEST_API_WITH_ID, toBeDeleted.getId())
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NO_CONTENT.value());

    final List<PilotEntity> spaceshipsAfter = pilotRepository.findAll();

    assertThat(spaceshipsAfter)
        .doesNotContain(toBeDeleted)
        .hasSize(pilotsBefore.size() - 1);
  }
}
