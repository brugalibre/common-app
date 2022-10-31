package com.brugalibre.common.domain.config;

import com.brugalibre.persistence.user.UserDao;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {UserDao.class})
@EntityScan(basePackages = {"com.brugalibre.persistence"})
@ComponentScan(basePackages = {"com.brugalibre.domain"})
public class CommonAppPersistenceConfig {
   // no-op
}
