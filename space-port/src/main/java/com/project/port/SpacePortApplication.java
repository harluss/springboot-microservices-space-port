package com.project.port;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Space Port - Port API", version = "1.0",
    description = "Information about spaceships and their crews currently enjoying (or not) their downtime at the space port"))
@EnableFeignClients
@EnableEurekaClient
public class SpacePortApplication {

  public static void main(final String[] args) {
    SpringApplication.run(SpacePortApplication.class, args);
  }
}
