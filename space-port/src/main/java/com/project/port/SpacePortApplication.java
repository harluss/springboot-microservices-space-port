package com.project.port;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Space Port - Port API", version = "1.0",
    description = "Information about spaceships and their pilots currently enjoying (or not) their downtime in the port"))
public class SpacePortApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpacePortApplication.class, args);
  }
}
