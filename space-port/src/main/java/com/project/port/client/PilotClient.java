package com.project.port.client;

import com.project.port.config.CustomErrorDecoder;
import com.project.port.dto.pilot.AddPilotClientRequest;
import com.project.port.dto.pilot.PilotClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Client used for calling Space Cantina microservice.
 */
@FeignClient(name = "${feign.clients.cantina.name}", path = "${feign.clients.cantina.path}",
    configuration = {CustomErrorDecoder.class})
public interface PilotClient {

  /**
   * Makes a GET call to Cantina microservice to retrieve a list of pilots
   *
   * @return list of pilots from a client
   */
  @GetMapping
  List<PilotClientResponse> getPilots();

  /**
   * Makes a POST call to Cantina microservice to add new pilot
   *
   * @param addPilotClientRequest pilot client request containing details of new pilot
   * @return newly added pilot
   */
  @PostMapping
  PilotClientResponse addPilot(@Valid @RequestBody final AddPilotClientRequest addPilotClientRequest);
}
