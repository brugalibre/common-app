package com.brugalibre.common.app.config.security;

import com.brugalibre.common.domain.config.CommonAppPersistenceConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.brugalibre.common.security"})
@Import({CommonAppPersistenceConfig.class})
public class CommonAppSecurityConfig {
// no-op
}
