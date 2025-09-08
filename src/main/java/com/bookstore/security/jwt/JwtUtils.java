package com.bookstore.security.jwt;

import com.bookstore.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtils {

    @Value("${application.jwt.secret-key-str}")
    private String secretKeyStr;

    @Value("${application.jwt.access-expiration-ms}")
    private Long accessExpirationMs;


    private SecretKey secretKey;

    @PostConstruct
    void init() {
        byte[] decodedSecretKey = Decoders.BASE64.decode(secretKeyStr);
        this.secretKey = Keys.hmacShaKeyFor(decodedSecretKey);
    }


    public String createAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getUserRole().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpirationMs))
                .signWith(secretKey)
                .compact();
    }

    public String extractUserEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }


}
