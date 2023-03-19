package com.project.port.client;

import com.project.port.config.CustomErrorDecoder;
import com.project.port.dto.spaceship.AddSpaceshipClientRequest;
import com.project.port.dto.spaceship.SpaceshipClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Client used for calling Space Hangar microservice.
 */
@FeignClient(name = "${feign.clients.hangar.name}", path = "${feign.clients.hangar.path}",
    configuration = {CustomErrorDecoder.class})
public interface SpaceshipClient {

  /**
   * Makes a GET call to Hangar microservice to retrieve a list of spaceships
   *
   * @return list of spaceships from a client
   */
  @GetMapping
  List<SpaceshipClientResponse> getSpaceships();

  /**
   * Makes a GET call to Hangar microservice to retrieve a spaceship by provided id
   *
   * @param spaceshipId spaceship id
   * @return spaceship
   */
  @GetMapping("{id}")
  Optional<SpaceshipClientResponse> getSpaceshipById(@NotBlank @PathVariable("id") final UUID spaceshipId);

  /**
   * Makes a POST request to Hangar microservice to add new spaceship
   *
   * @param addSpaceshipClientRequest spaceship client request containing details of new spaceship
   * @return newly added spaceship
   */
  @PostMapping
  SpaceshipClientResponse addSpaceship(@Valid @RequestBody final AddSpaceshipClientRequest addSpaceshipClientRequest);
}
