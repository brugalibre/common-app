package com.brugalibre.common.security.auth.config;

import com.brugalibre.common.domain.app.config.CommonAppPersistenceConfig;
import com.brugalibre.common.security.app.config.CommonAppSecurityConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableAutoConfiguration
@Configuration
@Import({CommonAppSecurityConfig.class, CommonAppPersistenceConfig.class})
public class TestCommonAppSecurityConfig {
   @Bean
   CorsConfigurationSource corsConfigurationSource() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
      return source;
   }
}
