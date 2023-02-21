package com.project.port.client;

import com.project.port.dto.PilotClientRequest;
import com.project.port.dto.PilotClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "pilots", url = "localhost:8081")
public interface PilotClient {

  @PostMapping("/api/pilots/crew")
  List<PilotClientResponse> getPilotsByIds(@RequestBody PilotClientRequest pilotIClientRequest);
}
