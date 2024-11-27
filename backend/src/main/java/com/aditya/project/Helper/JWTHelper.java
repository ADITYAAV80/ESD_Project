package com.aditya.project.Helper;

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


    //header[contains algorithm].payload[actual data].signature[secret key]
    private final String SECRET_KEY = "some random text secret bhuhu hihihih";

    //2 hours
    private final long EXPIRATION_TIME = 1000 * 60 * 60 *2;

    //converts secret key into byte array
    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //creates a Jwt token
    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .addClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    //uses the above function
    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, email);
    }

    //extract claims gets payload
    public Claims extractClaims(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder() // Use parserBuilder()
                    .setSigningKey(getSigningKey()) // Set the signing key
                    .build() // Build the parser
                    .parseClaimsJws(token) // Parse the JWS [interesting it actually checks for expiration as well hence try catch blocks to deal with user logout]
                    .getBody();
        } catch (Exception e) {
            return null;
        }
        return claims;
    }

    //from payload get these things
    public String extractUsername(String token) {
        return extractClaims(token) != null ?  extractClaims(token).getSubject() : null;
    }
    public Date extractExpiration(String token) {
        return extractClaims(token) != null ? extractClaims(token).getExpiration() : null;
    }
    public Boolean isTokenExpired(String token) {
        return extractClaims(token) != null ? extractExpiration(token).before(new Date()) : false;
    }

    //validate token expiry
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    //validate the header in token
    public Boolean validateAuthorizationHeader(String authorizationHeader) {
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authorizationHeader.substring(7);
        String user = extractUsername(token);
        return user != null && validateToken(token);
    }
}
