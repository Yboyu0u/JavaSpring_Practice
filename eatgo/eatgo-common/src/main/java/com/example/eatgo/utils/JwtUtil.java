package com.example.eatgo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.Security;


public class JwtUtil {

    private Key key;

    public JwtUtil(String secret){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String createToken(Long userId, String  name, Long restaurantId) {
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId)
                .claim("name", name);
        if(restaurantId != null){
            builder = builder.claim("restaurantId",restaurantId);
        }
        String token = builder
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
}
