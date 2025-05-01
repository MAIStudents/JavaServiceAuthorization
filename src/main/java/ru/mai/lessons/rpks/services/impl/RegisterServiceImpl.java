package ru.mai.lessons.rpks.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.dto.response.TokenResponse;
import ru.mai.lessons.rpks.models.User;
import ru.mai.lessons.rpks.repositories.UserRepository;
import ru.mai.lessons.rpks.services.RegisterService;
import ru.mai.lessons.rpks.services.UserService;

import java.time.Duration;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

  private final UserService userService;
  private final UserRepository userRepository;

  @Value("${token.signing.secret}")
  private String secret;

  @Value("${token.issuer}")
  private String issuer;

  @Value("${token.duration}")
  private Duration duration;

  @Override
  public TokenResponse register(String username) {

    if (userRepository.findByUsername(username).isPresent()) {
      log.warn("User has been registered");
      return new TokenResponse();
    }

    userService.createUser(User.builder().username(username).build());

    String token = JWT.create()
            .withIssuer(issuer)
            .withSubject(username)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + duration.toMillis()))
            .sign(Algorithm.HMAC256(secret));


    return new TokenResponse(token);
  }
}
