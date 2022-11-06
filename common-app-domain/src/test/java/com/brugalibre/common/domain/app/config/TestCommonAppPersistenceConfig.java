package com.brugalibre.common.domain.app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Configuration
@Import(CommonAppPersistenceConfig.class)
public class TestCommonAppPersistenceConfig {
   //no-op
}
