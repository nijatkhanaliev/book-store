package com.bookstore.security.jwt;

import com.bookstore.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtUtils {

    @Value("${application.jwt.secret-key-str}")
    private String secretKeyStr;

    private SecretKey secretKey;

    @PostConstruct
    void init(){
        byte[] decodedSecretKey = Decoders.BASE64.decode(secretKeyStr);
        this.secretKey = Keys.hmacShaKeyFor(decodedSecretKey);
    }


    public String createJwt(User user){
//        return Jwts.builder()
//                .subject(user.getEmail())
//                .claim("id",)

        return null;
    }


}
