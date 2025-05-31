package com.org.apiservices.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {

    private final String secretKey = "your-256-bit-secret-key-your-256-bit"; // Replace with a secure 256-bit key
    private final long expirationTime = 60 * 60 * 1000; // 1 hour in milliseconds
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
    
    

    /**
     * Generate a JWT token.
     *
     * @param claims Map of claims to include in the token.
     * @param subject The subject of the token (e.g., username or user ID).
     * @return The generated JWT token.
     */
    public String generateToken(Map<String, Object> claims, String subject) {
    	System.out.println("Key length: " + secretKey.getBytes().length * 8 + " bits");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate a JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Invalid or expired JWT token: " + e.getMessage());
            return false;
        }
    }

    /**
     * Extract claims from a JWT token.
     *
     * @param token The JWT token.
     * @return A map of claims extracted from the token.
     */
    public Map<String, Object> extractClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return new HashMap<>(claims);
    }
}