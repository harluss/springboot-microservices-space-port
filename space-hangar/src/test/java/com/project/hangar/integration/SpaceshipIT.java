package com.project.hangar.integration;

import com.project.hangar.common.BaseIT;
import com.project.hangar.dto.SpaceshipRequest;
import com.project.hangar.entity.SpaceshipEntity;
import com.project.hangar.repository.SpaceshipRepository;
import io.restassured.common.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.project.hangar.common.Constants.buildRequest;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@AutoConfigureMockMvc
class SpaceshipIT extends BaseIT {

  private static final String TEST_API = "/api/v1/spaceships";

  private static final String TEST_API_WITH_ID = "/api/v1/spaceships/{id}";

  private List<SpaceshipEntity> spaceshipsBefore;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SpaceshipRepository spaceshipRepository;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
    spaceshipsBefore = spaceshipRepository.findAll();
  }

  @Test
  void getSpaceships_happyPath() {

    final List<SpaceshipEntity> actual = given()
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

    assertThat(actual).hasSize(spaceshipsBefore.size());
  }

  @Test
  void getSpaceshipById_happyPath() {
    final SpaceshipEntity expected = spaceshipsBefore.get(0);

    final SpaceshipEntity actual = given()
        .when()
        .get(TEST_API_WITH_ID, expected.getId())
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .body()
        .as(SpaceshipEntity.class);

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

  @Test
  void addSpaceship_happyPath() {
    final SpaceshipRequest request = buildRequest();

    final SpaceshipEntity actual = given()
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
        .as(SpaceshipEntity.class);

    final List<SpaceshipEntity> spaceshipsAfter = spaceshipRepository.findAll();

    assertThat(spaceshipsBefore).doesNotContain(actual);
    assertThat(spaceshipsAfter)
        .contains(actual)
        .hasSize(spaceshipsBefore.size() + 1);
    assertThat(request).usingRecursiveComparison().isEqualTo(actual);
  }

  @Test
  void updateSpaceshipById_happyPath() {
    final SpaceshipRequest request = buildRequest();
    final SpaceshipEntity toBeUpdated = spaceshipsBefore.get(0);

    final SpaceshipEntity actual = given()
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
        .as(SpaceshipEntity.class);

    final List<SpaceshipEntity> spaceshipsAfter = spaceshipRepository.findAll();

    assertThat(toBeUpdated.getId()).isEqualTo(actual.getId());
    assertThat(spaceshipsBefore)
        .contains(toBeUpdated)
        .doesNotContain(actual);
    assertThat(spaceshipsAfter)
        .hasSameSizeAs(spaceshipsBefore)
        .doesNotContain(toBeUpdated)
        .contains(actual);
  }

  @Test
  void deleteSpaceshipById_happyPath() {
    final SpaceshipEntity toBeDeleted = spaceshipsBefore.get(0);

    given()
        .when()
        .delete(TEST_API_WITH_ID, toBeDeleted.getId())
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.NO_CONTENT.value());

    final List<SpaceshipEntity> spaceshipsAfter = spaceshipRepository.findAll();

    assertThat(spaceshipsAfter)
        .doesNotContain(toBeDeleted)
        .hasSize(spaceshipsBefore.size() - 1);
  }
}
