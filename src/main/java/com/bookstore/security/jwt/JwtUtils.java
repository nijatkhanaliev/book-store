package com.bookstore.security.jwt;

import com.bookstore.config.cache.RedisService;
import com.bookstore.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${application.jwt.secret-key-str}")
    private String secretKeyStr;

    @Value("${application.jwt.access-expiration-ms}")
    private Long accessExpirationMs;

    @Value("${application.jwt.refresh-expiration-ms}")
    private Long refreshExpirationMs;

    private final RedisService redisService;

    private SecretKey secretKey;

    @PostConstruct
    void init() {
        byte[] decodedSecretKey = Decoders.BASE64.decode(secretKeyStr);
        this.secretKey = Keys.hmacShaKeyFor(decodedSecretKey);
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getUserRole());
        String accessToken = createToken(user.getEmail(), claims, accessExpirationMs);
        redisService.saveAccessToken(accessToken, accessExpirationMs, user.getEmail());

        return accessToken;
    }

    public String generateRefreshToken(User user) {
        String refreshToken = createToken(user.getEmail(), null, refreshExpirationMs);
        redisService.saveRefreshToken(refreshToken, refreshExpirationMs, user.getEmail());

        return refreshToken;
    }

    private String createToken(String userEmail, Map<String, Object> claims, Long expirationMs) {
        return Jwts.builder()
                .subject(userEmail)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaim(token);

        return resolver.apply(claims);
    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        return Objects.equals(extractUserEmail(token), userDetails.getUsername()) &&
                isTokenExpired(token) &&
                redisService.isAccessTokenValid(userDetails.getUsername(), token);
    }

    public boolean isRefreshTokenValid(String token, UserDetails userDetails) {
        return Objects.equals(extractUserEmail(token), userDetails.getUsername()) &&
                isTokenExpired(token) &&
                redisService.isRefreshTokenValid(userDetails.getUsername(), token);
    }

    private boolean isTokenExpired(String token) {
        return !extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
