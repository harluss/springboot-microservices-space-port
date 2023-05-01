package com.project.gateway.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.project.gateway.service.TokenService;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  @Value("${jwt.expires-in}")
  private Integer expiresIn;

  private final JwtEncoder encoder;

  @Override
  public String generateToken(final Authentication authentication) {
    final Instant now = Instant.now();

    final JwtClaimsSet claims = JwtClaimsSet
        .builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plus(expiresIn, ChronoUnit.MINUTES))
        .subject(authentication.getName())
        .build();

    final JwtEncoderParameters encoderParams = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(),
        claims);

    return encoder.encode(encoderParams).getTokenValue();
  }
}
