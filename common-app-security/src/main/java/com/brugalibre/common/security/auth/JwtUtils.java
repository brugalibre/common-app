package com.brugalibre.common.security.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtUtils {
   @Value("${application.security.jwtSecret:null}")
   private String jwtSecret;
   @Value("${application.security.jwtExpirationMs:0}")
   private int jwtExpirationMs;

   public String generateJwtToken(UserDetails userDetails) {
      Date issuedAt = new Date();
      return Jwts.builder()
              .subject(userDetails.getUsername())
              .issuedAt(issuedAt)
              .expiration(new Date(issuedAt.getTime() + jwtExpirationMs))
              .signWith(getSecretKey())
              .compact();
   }

   public String getUserNameFromJwtToken(String authToken) {
      return getClaimsJws(authToken)
              .getPayload()
              .getSubject();
   }

   public boolean validateJwtToken(String authToken) {
      try {
         getClaimsJws(authToken);
      } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
               IllegalArgumentException e) {
         return false;
      }
      return true;
   }

   private Jws<Claims> getClaimsJws(String authToken) {
      return Jwts.parser()
              .verifyWith(getSecretKey())
              .build()
              .parseSignedClaims(authToken);
   }

   private SecretKey getSecretKey() {
      byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
      return Keys.hmacShaKeyFor(keyBytes);
   }
}
