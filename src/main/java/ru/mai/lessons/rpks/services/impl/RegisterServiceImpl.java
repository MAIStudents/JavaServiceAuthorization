package ru.mai.lessons.rpks.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.config.TokenConfig;
import ru.mai.lessons.rpks.dto.response.TokenResponse;
import ru.mai.lessons.rpks.models.User;
import ru.mai.lessons.rpks.services.RegisterService;
import ru.mai.lessons.rpks.services.UserService;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

  private final UserService userService;
  private final TokenConfig tokenConfig;

  @Override
  public TokenResponse register(String username) {
    if (userService.findUserByUsername(username).isPresent()) {
      throw new IllegalArgumentException("User with name " + username + " already exists");
    }

    userService.createUser(User.builder().username(username).build());

    String token = JWT.create()
            .withSubject(username)
            .withIssuer(tokenConfig.getIssuer())
            .withIssuedAt(new Date())
            .withExpiresAt(calculateExpirationDate(tokenConfig.getExpirationTime()))
            .sign(Algorithm.HMAC256(tokenConfig.getSigningSecret()));

    return new TokenResponse(token);
  }

  private Date calculateExpirationDate(Duration expirationTime) {
    return Date.from(Instant.now().plus(expirationTime));
  }
}
