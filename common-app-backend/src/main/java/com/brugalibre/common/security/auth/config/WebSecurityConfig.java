package com.brugalibre.common.security.auth.config;

import com.brugalibre.common.security.auth.AuthEntryPointJwt;
import com.brugalibre.common.security.auth.AuthTokenFilter;
import com.brugalibre.common.security.auth.JwtUtils;
import com.brugalibre.common.security.user.repository.UserRepository;
import com.brugalibre.persistence.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

   private final AntMatcherHelper antMatcherHelper;
   private final UserRepository userRepository;
   private final AuthEntryPointJwt unauthorizedHandler;
   private final AuthTokenFilter authTokenFilter;

   @Autowired
   public WebSecurityConfig(AntMatcherHelper antMatcherHelper, UserRepository userRepository,
                            AuthEntryPointJwt unauthorizedHandler, JwtUtils jwtUtils) {
      this.antMatcherHelper = antMatcherHelper;
      this.userRepository = userRepository;
      this.unauthorizedHandler = unauthorizedHandler;
      this.authTokenFilter = new AuthTokenFilter(jwtUtils, userRepository);
   }

   @Bean
   public AuthEntryPointJwt getAuthEntryPointJwt(AuthEntryPointJwt authEntryPointJwt) {
      return authEntryPointJwt;
   }

   @Bean
   public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

      authProvider.setUserDetailsService(userRepository);
      authProvider.setPasswordEncoder(passwordEncoder());

      return authProvider;
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
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.cors().and().csrf().disable()
              .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
              .and()
              .authorizeRequests()
              .antMatchers(antMatcherHelper.getAntMatcherForRole(Role.ADMIN.name())).hasRole(Role.ADMIN.name())
              .antMatchers(antMatcherHelper.getAntMatcherForRole(Role.USER.name())).hasRole(Role.USER.name())
              .antMatchers("/api/auth/**").permitAll()
              .antMatchers("/favicon.ico").permitAll()
              .antMatchers("/h2-console/**").permitAll()
              .anyRequest().authenticated()
              .and().headers().frameOptions().sameOrigin()
              .and()
              .formLogin()
              .loginProcessingUrl("/login")
              .defaultSuccessUrl(antMatcherHelper.getDefaultSuccessUrl(), true)
              .failureUrl("/login.html?error=true")
              .and()
              .logout()
              .logoutUrl("/perform_logout")
              .deleteCookies("JSESSIONID");
      return http.build();
   }

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
      return authConfiguration.getAuthenticationManager();
   }
}
