package com.brugalibre.common.security.auth.config;

import com.brugalibre.common.security.auth.AuthEntryPointJwt;
import com.brugalibre.common.security.auth.AuthTokenFilter;
import com.brugalibre.common.security.auth.JwtUtils;
import com.brugalibre.common.security.user.repository.UserDetailsServiceImpl;
import com.brugalibre.persistence.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.DefaultAuthorizationManagerFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class WebSecurityConfig {

   private final UserDetailsServiceImpl userDetailsServiceImpl;
   private final AuthTokenFilter authTokenFilter;

   @Value("${server.servlet.context-path:/}")
   private String contextPath;

   private static final String[] WEB_RESOURCES = {
           "/css/**",
           "/images/**",
           "/img/**",
           "/assets/**",
           "/fonts/**",
           "/js/**",
           "/scripts/**",
           "/favicon.ico",
           "/index.html",
   };

   @Autowired
   public WebSecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, JwtUtils jwtUtils) {
      this.userDetailsServiceImpl = userDetailsServiceImpl;
      this.authTokenFilter = new AuthTokenFilter(jwtUtils, userDetailsServiceImpl);
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http, AuthEntryPointJwt unauthorizedHandler, WebSecurityConfigHelper webSecurityConfigHelper) {
      http.cors(Customizer.withDefaults())
              .csrf(AbstractHttpConfigurer::disable)
              .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(unauthorizedHandler))
              .sessionManagement(sessionMgmtConfigurer -> sessionMgmtConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(authorize -> authorize
                      .requestMatchers(WEB_RESOURCES).permitAll()
                      .requestMatchers("/api/brugalibre/common/v1/auth/**").permitAll()
                      .requestMatchers("/error**").permitAll()
                      .requestMatchers("/h2-console/**").permitAll()
                      .requestMatchers(webSecurityConfigHelper.getOptionalPermittedPatterns()).permitAll()
                      .requestMatchers(webSecurityConfigHelper.getRequestMatcherForRole(Role.USER.name())).hasAuthority(Role.USER.name())
                      .requestMatchers(webSecurityConfigHelper.getRequestMatcherForRole(Role.ADMIN.name())).hasAuthority(Role.ADMIN.name())
                      .requestMatchers(contextPath).permitAll()  // grant access to staging area -> web-router can re route  to login
                      .anyRequest().authenticated())
              .headers(headersConfigurer -> headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
              .formLogin(configurer -> configurer.loginProcessingUrl(webSecurityConfigHelper.getLoginProcessingUrl())
                      .defaultSuccessUrl(contextPath, true))
              .logout(logoutConfigurer -> logoutConfigurer.deleteCookies("JSESSIONID")
                      .logoutUrl("/logout"));
      http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
      return http.build();
   }

   @Bean
   public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsServiceImpl);
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
   }

   // Adding the prefix ROLE_ to the Role enum and working with hasRole(Role.USER.name() didn't worked..
   @Bean
   public GrantedAuthorityDefaults grantedAuthorityDefaults() {
      return new GrantedAuthorityDefaults(""); // Remove the ROLE_ prefix ffs!
   }

   @Bean
   public DefaultAuthorizationManagerFactory<?> defaultAuthorizationManagerFactory() {
      DefaultAuthorizationManagerFactory<?> defaultAuthorizationManagerFactory = new DefaultAuthorizationManagerFactory<>();
      defaultAuthorizationManagerFactory.setRolePrefix("");
      return defaultAuthorizationManagerFactory;
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthTokenFilter authenticationJwtTokenFilter() {
      return authTokenFilter;
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
      return authConfiguration.getAuthenticationManager();
   }
}
