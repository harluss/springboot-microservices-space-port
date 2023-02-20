package com.project.port.client;

import com.project.port.dto.PilotClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "pilots", url = "localhost:8081")
public interface PilotClient {

  @GetMapping("/api/pilots")
  List<PilotClientResponse> getPilots();
}
