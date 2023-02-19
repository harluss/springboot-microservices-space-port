package com.project.hangar.common;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class BaseIT extends TestUtil {

  private static final String POSTGRES_IMAGE = "postgres:15-alpine";

  private static final String DB_NAME = "space_port_test_db";

  private static final String SQL_SCRIPT_FILE = "schema.sql";

  @Container
  static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse(POSTGRES_IMAGE))
      .withDatabaseName(DB_NAME)
      .withInitScript(SQL_SCRIPT_FILE);

  @DynamicPropertySource
  static void setDatasourceProperties(final DynamicPropertyRegistry propertyRegistry) {
    propertyRegistry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
    propertyRegistry.add("spring.datasource.username", POSTGRES_CONTAINER::getUsername);
    propertyRegistry.add("spring.datasource.password", POSTGRES_CONTAINER::getPassword);
  }
}
