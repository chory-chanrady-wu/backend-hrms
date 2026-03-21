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

    @Value("${jwt.secret}")
    private String jwtSecretString;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpirationMs; // 1 hour in milliseconds

    @Value("${jwt.refresh.expiration:604800000}")
    private long jwtRefreshExpirationMs; // 7 days in milliseconds

    private SecretKey secretKey;
    private SecretKey getSecretKey() {
        if (secretKey == null) {
            if (jwtSecretString != null && jwtSecretString.length() >= 64) {
                try {
                    byte[] decodedKey = Base64.getDecoder().decode(jwtSecretString);
                    if (decodedKey.length * 8 >= 512) {
                        secretKey = Keys.hmacShaKeyFor(decodedKey);
                    } else {
                        byte[] keyBytes = jwtSecretString.getBytes();
                        if (keyBytes.length * 8 >= 512) {
                            secretKey = Keys.hmacShaKeyFor(keyBytes);
                        } else {
                            secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                        }
                    }
                } catch (IllegalArgumentException e) {
                    byte[] keyBytes = jwtSecretString.getBytes();
                    if (keyBytes.length * 8 >= 512) {
                        secretKey = Keys.hmacShaKeyFor(keyBytes);
                    } else {
                        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
                    }
                }
            } else {
                secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            }
        }
        return secretKey;
    }

    public String generateAccessToken(UserDTO user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRoleName());
        claims.put("email", user.getEmail());
        claims.put("fullName", user.getFullName());

        return createToken(claims, user.getUsername(), jwtExpirationMs);
    }

    public String generateRefreshToken(UserDTO user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        return createToken(claims, user.getUsername(), jwtRefreshExpirationMs);
    }

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

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Claims getAllClaimsFromToken(String token) {
        SecretKey key = getSecretKey();
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

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
    public long getTokenExpirationSeconds() {
        return jwtExpirationMs / 1000;
    }
}

