package ru.mai.lessons.rpks.models;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

public record UserClaims(
        String token,
        String subject,
        Map<String, Claim> claims,
        Date expiresAt,
        Date issuedAt
) {

    public UserClaims(DecodedJWT decodedJWT) {
        this(
                decodedJWT.getToken(),
                decodedJWT.getSubject(),
                decodedJWT.getClaims(),
                decodedJWT.getExpiresAt(),
                decodedJWT.getIssuedAt()
        );
    }
}