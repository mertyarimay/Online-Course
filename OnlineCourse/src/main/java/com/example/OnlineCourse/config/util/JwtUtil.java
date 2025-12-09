package com.example.OnlineCourse.config.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtUtil {

    private final String SECRET_KEY = "your_secret_key"; // Gizli anahtar
    private final long EXPIRATION_TIME = 3600000; // 1 saat (ms)

    // Token oluşturma metodu
    public String generateToken(String username, String userId, String roleName) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        // Rol başında "ROLE_" yoksa ekle
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        return JWT.create()
                .withSubject(username)
                .withClaim("userId", userId)
                .withClaim("role", roleName)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }

    // Token'dan userId çek
    public String extractUserId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userId").asString();
    }

    // Token'dan username çek
    public String extractUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    // Token'dan tekil role çek
    public String extractRole(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("role").asString();
    }

    // Token geçerliliğini kontrol et
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return !isTokenExpired(jwt);
        } catch (Exception e) {
            return false;
        }
    }

    // Token süresi dolmuş mu?
    private boolean isTokenExpired(DecodedJWT jwt) {
        return jwt.getExpiresAt().before(new Date());
    }
}



