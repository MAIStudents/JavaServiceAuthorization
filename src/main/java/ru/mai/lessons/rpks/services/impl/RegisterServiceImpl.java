package ru.mai.lessons.rpks.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.dto.response.TokenResponse;
import ru.mai.lessons.rpks.models.User;
import ru.mai.lessons.rpks.security.TokenAuthentication;
import ru.mai.lessons.rpks.services.RegisterService;
import ru.mai.lessons.rpks.services.UserService;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

  private final UserService userService;

  @Value("${token.signing.secret}")
  private String secret;

  @Value("${token.issuer}")
  private String issuer;

  @Override
  public TokenResponse register(String username) {

    String token = generateJWT(username);
    if (token == null) return null;
    User user = new User(null, username);
    userService.createUser(user);
    return new TokenResponse(token);
    
  }

  private String generateJWT(String username) {
    String token;
    try {
      token = JWT.create()
              .withIssuer(issuer)
              .withSubject(username)
              .withIssuedAt(new Date())
              .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
              .sign(Algorithm.HMAC256(secret));
    } catch (JWTCreationException e) {
      log.info(e.getMessage());
      return null;
    }
    return token;
  }
}
