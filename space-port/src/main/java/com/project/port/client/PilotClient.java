package com.project.port.client;

import com.project.port.dto.PilotClientRequest;
import com.project.port.dto.PilotClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Provides methods for REST calls to Cantina microservice
 */
@FeignClient(name = "pilots", url = "localhost:8081")
public interface PilotClient {

  /**
   * Makes a POST call to a Cantina microservice to retrieve a list of pilots based on provided list of pilot Ids
   *
   * @param pilotIClientRequest pilot client request containing list of pilot Ids
   * @return list of pilot responses from a client
   */
  @PostMapping("/api/pilots/crew")
  List<PilotClientResponse> getPilotsByIds(@RequestBody PilotClientRequest pilotIClientRequest);
}
