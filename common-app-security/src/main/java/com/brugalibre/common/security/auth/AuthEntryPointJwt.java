package com.brugalibre.common.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Service
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
   private static final Logger LOG = LoggerFactory.getLogger(AuthEntryPointJwt.class);

   @Value("${application.security.unauthorizedRedirect:}")
   private String unauthorizedRedirect;

   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
      LOG.error("Unauthorized request {}, error: {}, unauthorizedRedirect: {}", request.getRequestURL(), authException.getMessage(), unauthorizedRedirect);
      if (StringUtils.hasText(unauthorizedRedirect)) {
         response.sendRedirect(unauthorizedRedirect);
      } else {
         response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
      }
   }
}
