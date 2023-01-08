package com.brugalibre.domain.user.service.userrole;

import com.brugalibre.common.domain.app.config.TestCommonAppPersistenceConfig;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.persistence.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TestCommonAppPersistenceConfig.class)
class UserRoleConfigServiceTest {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private UserRoleConfigService userRoleConfigService;

   @AfterEach
   public void cleanUp(){
      userRepository.deleteAll();
   }

   @Test
   void addAdminRoleIfMissing() {
      // Given
      String password = "abc";
      String username1 = "test-user";
      String username2 = "peterpan";
      User userWithAdminPermission = User.of(username1, password);
      User userWithoutAdminPermission = User.of(username2, password);

      // When
      userWithAdminPermission = userRepository.save(userWithAdminPermission);
      userWithoutAdminPermission = userRepository.save(userWithoutAdminPermission);
      assertThat(userWithAdminPermission.roles().size(), is(1));
      assertThat(userWithAdminPermission.roles().get(0), is(Role.USER));
      assertThat(userWithoutAdminPermission.roles().size(), is(1));
      assertThat(userWithoutAdminPermission.roles().get(0), is(Role.USER));
      userWithAdminPermission = userRoleConfigService.addMissingRoles(userWithAdminPermission.id());
      userWithoutAdminPermission = userRoleConfigService.addMissingRoles(userWithoutAdminPermission.id());

      // Then
      assertThat(userWithAdminPermission.roles().size(), is(2));
      assertThat(userWithAdminPermission.hasRole(Role.ADMIN), is(true));
      assertThat(userWithAdminPermission.password(), is(password));
      assertThat(userWithAdminPermission.username(), is(username1));
      assertThat(userWithoutAdminPermission.roles().size(), is(1));
      assertThat(userWithoutAdminPermission.hasRole(Role.ADMIN), is(false));
      assertThat(userWithoutAdminPermission.password(), is(password));
      assertThat(userWithoutAdminPermission.username(), is(username2));
   }
}