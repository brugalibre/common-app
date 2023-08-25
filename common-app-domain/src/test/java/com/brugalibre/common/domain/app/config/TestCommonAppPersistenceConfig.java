package com.brugalibre.common.domain.app.config;

import com.brugalibre.common.persistence.transaction.TransactionHelper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Configuration
@Import(CommonAppPersistenceConfig.class)
@ComponentScan(basePackages = "com.brugalibre.common.persistence.transaction")
public class TestCommonAppPersistenceConfig {
    //no-op
}
