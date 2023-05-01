package com.project.gateway.config;

import javax.crypto.spec.SecretKeySpec;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.nimbusds.jose.jwk.source.ImmutableSecret;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class Security {

  @Value("${jwt.secret}")
  private String jwtSecret;

  // In memory user for demonstration purposes
  @Bean
  public MapReactiveUserDetailsService user() {
    return new MapReactiveUserDetailsService(User
        .withUsername("Keef")
        .password("{bcrypt}$2a$10$d73TwPEfGgjMBdMu0iUFk.vSMOUBnTCDQZP/yUf3m.cMjKWmlmSA6")
        .roles("USER", "ADMIN")
        .build());
  }

  @Bean
  public ReactiveAuthenticationManager authenticationManager(final ReactiveUserDetailsService userDetailsService) {
    return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
  }

  @Bean
  public SecurityWebFilterChain securityFilterChain(final ServerHttpSecurity http) {
    return http
        .authorizeExchange(auth -> auth.pathMatchers("/api/auth/token").permitAll().anyExchange().authenticated())
        .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  ReactiveJwtDecoder jwtDecoder() {
    final SecretKeySpec secretKey = new SecretKeySpec(jwtSecret.getBytes(), "RSA");

    return NimbusReactiveJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS512).build();
  }

  @Bean
  JwtEncoder jwtEncoder() {
    return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecret.getBytes()));
  }
}
