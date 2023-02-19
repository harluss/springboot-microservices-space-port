package com.project.port.client;

import com.project.port.dto.SpaceshipClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "spaceship", url = "localhost:8082")
public interface SpaceshipClient {

  @GetMapping("/api/spaceships")
  List<SpaceshipClientResponse> getSpaceships();
}
