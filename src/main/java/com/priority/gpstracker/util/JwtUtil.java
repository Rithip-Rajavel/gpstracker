package com.priority.gpstracker.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

   @Value("${jwt.secret:mySecretKey123456789012345678901234567890}")
   private String jwtSecret;

   @Value("${jwt.expiration:86400000}")
   private Long jwtExpiration;

   private SecretKey getSigningKey() {
       return Keys.hmacShaKeyFor(jwtSecret.getBytes());
   }

   public String generateToken(String email, UUID userId) {
       return Jwts.builder()
               .subject(email)
               .claim("userId", userId.toString())
               .issuedAt(new Date())
               .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
               .signWith(getSigningKey())
               .compact();
   }

   public String getEmailFromToken(String token) {
       Claims claims = Jwts.parser()
               .verifyWith(getSigningKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();
       return claims.getSubject();
   }

   public UUID getUserIdFromToken(String token) {
       Claims claims = Jwts.parser()
               .verifyWith(getSigningKey())
               .build()
               .parseSignedClaims(token)
               .getPayload();
       String userIdStr = claims.get("userId", String.class);
       return UUID.fromString(userIdStr);
   }

   public boolean validateToken(String token) {
       try {
           Jwts.parser()
                   .verifyWith(getSigningKey())
                   .build()
                   .parseSignedClaims(token);
           return true;
       } catch (JwtException | IllegalArgumentException e) {
           return false;
       }
   }
}
