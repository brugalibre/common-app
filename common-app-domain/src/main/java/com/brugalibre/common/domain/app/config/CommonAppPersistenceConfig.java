package com.brugalibre.common.domain.app.config;

import com.brugalibre.persistence.contactpoint.mobilephone.dao.MobilePhoneDao;
import com.brugalibre.persistence.user.dao.UserDao;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = {UserDao.class, MobilePhoneDao.class})
@EntityScan(basePackages = {"com.brugalibre.common.domain.persistence", "com.brugalibre.persistence"})
@ComponentScan(basePackages = {"com.brugalibre.domain", "com.brugalibre.common.rest"})
public class CommonAppPersistenceConfig {
   // no-op
}
