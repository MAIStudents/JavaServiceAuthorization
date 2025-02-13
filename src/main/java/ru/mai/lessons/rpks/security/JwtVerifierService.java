package ru.mai.lessons.rpks.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.services.UserService;
import ru.mai.lessons.rpks.utils.TokenUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtVerifierService {

    private final UserService userService;

  @Value("${token.signing.secret}")
  private String secret;

  @Value("${token.issuer}")
  private String issuer;

  public boolean verify(String token) {
    if (token == null || token.isBlank()) {
      return false;
    }
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm)
              .withIssuer(issuer)
              .build();
      DecodedJWT decodedJWT = verifier.verify(token);
      String username = decodedJWT.getSubject();
      return userService.isUserExist(username);
    } catch (JWTVerificationException e) {
      log.error("JWT verification failed: {}", e.getMessage());
      return false;
    }
  }
}
