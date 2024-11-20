package com.aditya.project.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTHelper {
    private final String SECRET_KEY = "some random text secret bhuhu hihihih";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 12;
    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }
    public Claims extractClaims(String token) {
        return  Jwts.parserBuilder() // Use parserBuilder()
                .setSigningKey(getSigningKey()) // Set the signing key
                .build() // Build the parser
                .parseClaimsJws(token) // Parse the JWS
                .getBody();
    }
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public Boolean validateAuthorizationHeader(String authorizationHeader) {
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authorizationHeader.substring(7);
        String email = extractUsername(token);
        return email != null && validateToken(token);
    }
}
