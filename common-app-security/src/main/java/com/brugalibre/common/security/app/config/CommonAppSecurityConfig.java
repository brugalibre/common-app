package com.brugalibre.common.security.app.config;

import com.brugalibre.common.security.auth.config.WebSecurityConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.brugalibre.common.security"})
@Import({WebSecurityConfig.class})
public class CommonAppSecurityConfig {
// no-op
}
