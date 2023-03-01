package com.project.hangar.integration;

import com.project.hangar.common.BaseIT;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;

@AutoConfigureMockMvc
class HealthIT extends BaseIT {

  private static final String TEST_ACTUATOR_API = "/actuator/health";

  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.mockMvc(mockMvc);
  }

  @Test
  void healthCheck_happyPath() {

    given()
        .when()
        .get(TEST_ACTUATOR_API)
        .then()
        .log().body()
        .assertThat()
        .statusCode(HttpStatus.OK.value())
        .body("status", equalToIgnoringCase("up"));
  }
}
