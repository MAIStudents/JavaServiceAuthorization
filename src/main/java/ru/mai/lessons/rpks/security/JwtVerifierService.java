package ru.mai.lessons.rpks.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.services.UserService;

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
    if (token == null || token.trim().isEmpty()) {
      log.warn("JWT token is null or empty");
      return false;
    }

    Algorithm algorithm = Algorithm.HMAC256(secret);

    try {
      JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .build();

      DecodedJWT decodedJWT = verifier.verify(token);
      String username = decodedJWT.getSubject();
      userService.loadUserByUsername(username);

      return true;
    } catch (TokenExpiredException e) {
      log.error("Token expired: {}", e.getMessage());
    } catch (JWTVerificationException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (UsernameNotFoundException e) {
      log.error("User not found in database: {}", e.getMessage());
    }

    return false;
  }
}
