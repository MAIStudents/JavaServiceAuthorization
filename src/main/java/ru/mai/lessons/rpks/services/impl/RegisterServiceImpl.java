package ru.mai.lessons.rpks.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.mai.lessons.rpks.dto.response.TokenResponse;
import ru.mai.lessons.rpks.models.User;
import ru.mai.lessons.rpks.services.RegisterService;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserServiceImpl userService;

    @Value("${token.signing.secret}")
    private String secret;

    @Value("${token.issuer}")
    private String issuer;

    @Override
    public TokenResponse register(String username) {
        User user = new User();
        user.setUsername(username);
        userService.createUser(user);

        return new TokenResponse(generateJwtToken(username));
    }

    private String generateJwtToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer(issuer)
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .sign(algorithm);
    }
}