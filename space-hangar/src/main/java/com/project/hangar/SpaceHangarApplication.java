package com.project.hangar;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Space Port - Hangar API", version = "1.0", description = "Information about currently hangared Spaceships"))
public class SpaceHangarApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpaceHangarApplication.class, args);
  }
}
