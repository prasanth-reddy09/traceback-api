package com.traceback.api.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * The "Keycard Machine". Handles generating, reading, and validating JWT tokens.
 */
@Component
public class JwtUtil {

    // 🔒 In a real company, this is hidden in environment variables! 
    // It must be at least 256-bit (32 characters) long.
    private static final String SECRET_KEY = "TracebackSuperSecretKeyForJwtGenerationMustBeLongEnough!";

    // Converts our string key into a cryptographic Key object
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 1. Create a new token for a user who just logged in
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // We store the user's email inside the token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Expires in 24 hours
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign it cryptographically
                .compact();
    }

    // 2. Read the email hidden inside an existing token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 3. Verify if the token is valid and hasn't expired
    public boolean isTokenValid(String token, String userEmail) {
        final String email = extractEmail(token);
        return (email.equals(userEmail) && !isTokenExpired(token));
    }

    // Helper method to check expiration
    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}