package com.project.port.client;

import com.project.port.dto.SpaceshipClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Provides methods for REST calls to Hangar microservice
 */
@FeignClient(name = "${feign.clients.hangar.name}", path = "${feign.clients.hangar.url}")
public interface SpaceshipClient {

  /**
   * Makes a GET call to a Hangar microservice to retrieve a list of spaceships
   *
   * @return list of spaceships from a client
   */
  @GetMapping
  List<SpaceshipClientResponse> getSpaceships();
}
