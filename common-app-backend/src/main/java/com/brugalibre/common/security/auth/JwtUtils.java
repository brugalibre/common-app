package com.brugalibre.common.security.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtils {
   @Value("${application.security.jwtSecret}")
   private String jwtSecret;
   @Value("${application.security.jwtExpirationMs}")
   private int jwtExpirationMs;

   public String generateJwtToken(Authentication authentication) {
      UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
      return Jwts.builder()
              .setSubject((userPrincipal.getUsername()))
              .setIssuedAt(new Date())
              .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
              .signWith(SignatureAlgorithm.HS512, jwtSecret)
              .compact();
   }

   public String getUserNameFromJwtToken(String token) {
      return Jwts.parser().setSigningKey(jwtSecret)
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
   }

   public boolean validateJwtToken(String authToken) {
      try {
         Jwts.parser()
                 .setSigningKey(jwtSecret)
                 .parseClaimsJws(authToken);
      } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
         return false;
      }
      return true;
   }
}
