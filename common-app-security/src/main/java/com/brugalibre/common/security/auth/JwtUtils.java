package com.brugalibre.common.security.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
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
              .setSubject(userDetails.getUsername())
              .setIssuedAt(issuedAt)
              .setExpiration(new Date(issuedAt.getTime() + jwtExpirationMs))
              .signWith(getSecretKey(), SignatureAlgorithm.HS512)
              .compact();
   }

   public String getUserNameFromJwtToken(String authToken) {
      return getClaimsJws(authToken)
              .getBody()
              .getSubject();
   }

   public boolean validateJwtToken(String authToken) {
      try {
         getClaimsJws(authToken);
      } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
         return false;
      }
      return true;
   }

   private Jws<Claims> getClaimsJws(String authToken) {
      return Jwts.parserBuilder()
              .setSigningKey(getSecretKey())
              .build()
              .parseClaimsJws(authToken);
   }

   private Key getSecretKey() {
      byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
      return Keys.hmacShaKeyFor(keyBytes);
   }
}
