package com.regysmendes.personalfinance.services.jwt;

import com.regysmendes.personalfinance.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    @Value("{8f3a92c1e6b74d05f9a1c3e8b2d6f4a09c7e5b3d1f8a6c4e2b9d7f5a3c1e8b6d}")
    private String secret;

    public String generateToken(User user) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(key)
                .compact();
    }

    public String validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        }
        catch (Exception e) {
            return null;
        }
    }

}