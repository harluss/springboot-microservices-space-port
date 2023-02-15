package com.project.hangar.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("api/spaceships")
public class SpaceshipController {

  @GetMapping
  public ResponseEntity<Void> getSpaceships() {
    log.info("getting spaceships");
    return ResponseEntity.ok().build();
  }
}
