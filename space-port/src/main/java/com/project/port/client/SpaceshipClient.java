package com.project.port.client;

import com.project.port.config.CustomErrorDecoder;
import com.project.port.dto.SpaceshipClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides methods for REST calls to Hangar microservice
 */
@FeignClient(name = "${feign.clients.hangar.name}", path = "${feign.clients.hangar.path}", configuration = {CustomErrorDecoder.class})
public interface SpaceshipClient {

  /**
   * Makes a GET call to a Hangar microservice to retrieve a list of spaceships
   *
   * @return list of spaceships from a client
   */
  @GetMapping
  List<SpaceshipClientResponse> getSpaceships();

  /**
   * Makes a GET call to a Hangar microservice to retrieve a spaceship by provided id
   *
   * @param spaceshipId spaceship id
   * @return spaceship
   */
  @GetMapping("{id}")
  Optional<SpaceshipClientResponse> getSpaceshipById(@NotBlank @PathVariable("id") final UUID spaceshipId);
}
