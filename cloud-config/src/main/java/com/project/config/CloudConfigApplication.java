package com.project.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class CloudConfigApplication {

  public static void main(final String[] args) {
    SpringApplication.run(CloudConfigApplication.class, args);
  }
}
