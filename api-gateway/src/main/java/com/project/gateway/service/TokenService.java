package com.project.gateway.service;

import org.springframework.security.core.Authentication;

/**
 * Provides methods for performing different operations on JWT tokens.
 */
public interface TokenService {

  /**
   * Returns JWT token
   *
   * @param authentication authentication
   * @return JWT token
   */
  String generateToken(final Authentication authentication);
}
