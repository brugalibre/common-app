package com.brugalibre.common.security.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
   private static final Logger LOG = LoggerFactory.getLogger(AuthEntryPointJwt.class);

   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
      LOG.error("Unauthorized request {}, error: {}", request.getRequestURL(), authException.getMessage());
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
   }
}
