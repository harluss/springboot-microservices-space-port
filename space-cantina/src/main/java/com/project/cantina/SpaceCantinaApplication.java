package com.project.cantina;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Space Port - Cantina API", version = "1.0",
    description = "Information about spaceships' pilots currently enjoying (or not) their downtime in the cantina"))
@EnableEurekaClient
public class SpaceCantinaApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpaceCantinaApplication.class, args);
  }
}

