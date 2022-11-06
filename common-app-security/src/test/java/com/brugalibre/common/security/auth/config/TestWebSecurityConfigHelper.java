package com.brugalibre.common.security.auth.config;

import org.springframework.stereotype.Service;

@Service
public class TestWebSecurityConfigHelper implements WebSecurityConfigHelper{
   @Override
   public String[] getRequestMatcherForRole(String role) {
      return new String[0];
   }

   @Override
   public String getDefaultSuccessUrl() {
      return "/";
   }

   @Override
   public String getLoginProcessingUrl() {
      return "/";
   }
}
