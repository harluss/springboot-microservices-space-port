package com.project.cantina;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Space Port - Cantina API", version = "1.0",
    description = "Information about spaceships' pilots currently enjoying (or not) their downtime in the cantina"))
public class SpaceCantinaApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpaceCantinaApplication.class, args);
  }
}

