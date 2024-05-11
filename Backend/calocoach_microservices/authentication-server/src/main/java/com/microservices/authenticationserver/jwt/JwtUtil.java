package com.microservices.authenticationserver.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Decoders.BASE64.decode
            ("JWTlearningisjhfihshidfbdsjajfbsdkjbasjdbgiuadgsnjnadsf"));

    public String generateToken(UserDetails userDetails, Long id) {
        String firstRole = userDetails.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("No authorities");

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", firstRole);
        claims.put("userId", id);
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60480000))
                .signWith(SECRET_KEY)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token,
                                    Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.
                parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public boolean validateToken(String jwtToken, UserDetails userDetails) {
        String userName = getUserNameFromToken(jwtToken);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


}
