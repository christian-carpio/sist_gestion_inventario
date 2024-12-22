package com.uees.mgra.apigateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {


    @Value("${jwt.secret}")
    private String secret;


    public void validateToken(final String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        verifier.verify(token);
    }
}
