package ru.mai.lessons.rpks.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.exception.ParseTokenException;
import ru.mai.lessons.rpks.repositories.UserRepository;
import ru.mai.lessons.rpks.services.UserService;

import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtVerifierService {

  // из-за наличия кеша UserService не подходит для тестов
  private final UserRepository userRepository;

  @Value("${token.signing.secret}")
  private String secret;

  @Value("${token.issuer}")
  private String issuer;

  public boolean verify(String token) {
    DecodedJWT jwt;
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm)
              .withIssuer(issuer)
              .build();
      
      jwt = verifier.verify(token);
    } catch (JWTVerificationException e) {
      log.warn("Bad credentials");
      throw new ParseTokenException(e.getMessage());
    }
    String username = jwt.getSubject();

    return userRepository.findByUsername(username).isPresent();
  }
}
