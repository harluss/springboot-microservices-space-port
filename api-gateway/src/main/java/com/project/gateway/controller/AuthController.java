package com.project.gateway.controller;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.gateway.dto.LoginRequest;
import com.project.gateway.service.TokenService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final TokenService tokenService;

  private final ReactiveAuthenticationManager authManager;

  @PostMapping("/token")
  public String token(@RequestBody final LoginRequest loginRequest) {

    final Mono<Authentication> authentication = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

    return tokenService.generateToken(authentication.share().block());
  }
}
