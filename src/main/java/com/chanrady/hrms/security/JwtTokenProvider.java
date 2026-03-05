package com.chanrady.hrms.security;

import com.chanrady.hrms.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:your_very_long_secret_key_that_is_at_least_512_bits_long_to_ensure_hs512_security_standards_are_met_with_proper_encryption}")
    private String jwtSecretString;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpirationMs; // 1 hour in milliseconds

    @Value("${jwt.refresh.expiration:604800000}")
    private long jwtRefreshExpirationMs; // 7 days in milliseconds

    private SecretKey secretKey;

    /**
     * Initialize secret key from configuration
     */
    private SecretKey getSecretKey() {
        if (secretKey == null) {
            // Use the configured secret if it's long enough (512+ bits = 64+ characters in Base64)
            if (jwtSecretString != null && jwtSecretString.length() >= 64) {
                try {
                    // Try to decode as Base64 first
                    byte[] decodedKey = Base64.getDecoder().decode(jwtSecretString);
                    if (decodedKey.length * 8 >= 512) {
                        secretKey = Keys.hmacShaKeyFor(decodedKey);
                    } else {
                        // If decoded key is too short, use the string bytes directly (hashed)
                        byte[] keyBytes = jwtSecretString.getBytes();
                        if (keyBytes.length * 8 >= 512) {
                            secretKey = Keys.hmacShaKeyFor(keyBytes);
                        } else {
                            // Fallback: generate a secure key
                            secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    // Not Base64 encoded, use string bytes directly
                    byte[] keyBytes = jwtSecretString.getBytes();
                    if (keyBytes.length * 8 >= 512) {
                        secretKey = Keys.hmacShaKeyFor(keyBytes);
                    } else {
                        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                    }
                }
            } else {
                // Generate a secure key if no proper secret is provided
                secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            }
        }
        return secretKey;
    }

    /**
     * Generate Access Token
     */
    public String generateAccessToken(UserDTO user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoleName());
        claims.put("email", user.getEmail());
        claims.put("fullName", user.getFullName());

        return createToken(claims, user.getUsername(), jwtExpirationMs);
    }

    /**
     * Generate Refresh Token
     */
    public String generateRefreshToken(UserDTO user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        return createToken(claims, user.getUsername(), jwtRefreshExpirationMs);
    }

    /**
     * Create JWT Token
     */
    private String createToken(Map<String, Object> claims, String subject, long expirationTime) {
        SecretKey key = getSecretKey();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extract username from token
     */
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    /**
     * Extract all claims from token
     */
    public Claims getAllClaimsFromToken(String token) {
        SecretKey key = getSecretKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Check if token is expired
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * Validate token
     */
    public Boolean validateToken(String token) {
        try {
            SecretKey key = getSecretKey();
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get expiration time in seconds
     */
    public long getTokenExpirationSeconds() {
        return jwtExpirationMs / 1000;
    }
}

