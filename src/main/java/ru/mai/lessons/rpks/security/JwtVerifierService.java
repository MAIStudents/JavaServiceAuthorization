package ru.mai.lessons.rpks.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.config.TokenConfig;
import ru.mai.lessons.rpks.models.User;
import ru.mai.lessons.rpks.services.UserService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtVerifierService {

  private final UserService userService;
  private final TokenConfig tokenConfig;

  public boolean verify(String token) {
    if (token == null || token.isBlank()) {
      log.error("Token is null or empty");
      return false;
    }

    try {
      DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(tokenConfig.getSigningSecret()))
              .withIssuer(tokenConfig.getIssuer())
              .build()
              .verify(token);

      String username = decodedJWT.getSubject();
      Optional<User> user = userService.findUserByUsername(username);
      if (user.isEmpty()) {
        log.error("User not found");
        return false;
      }

      log.info("Token verified successfully for user: {}", username);
      return true;

    } catch (JWTVerificationException ex) {
      log.warn("Invalid JWT token: {}", ex.getMessage());
      return false;
    } catch (Exception ex) {
      log.error("Unexpected error during JWT verification", ex);
      return false;
    }
  }
}
