package com.microservices.apigateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode
            ("JWTlearningisjhfihshidfbdsjajfbsdkjbasjdbgiuadgsnjnadsf"));

    public void validateToken(final String token) {
        Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
    }


}
