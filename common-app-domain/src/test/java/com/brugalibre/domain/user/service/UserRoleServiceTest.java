package com.brugalibre.domain.user.service;

import com.brugalibre.common.domain.app.config.TestCommonAppPersistenceConfig;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.persistence.user.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TestCommonAppPersistenceConfig.class)
class UserRoleServiceTest {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private UserRoleService userRoleService;

   @Test
   void addMissingRolesIfNecessary() {
      // Given
      String password = "abc";
      String username = "peterpan";
      User user = User.of(username, password);

      // When
      User savedUser = userRepository.save(user);
      assertThat(savedUser.roles().size(), is(1));
      assertThat(savedUser.roles().get(0), is(Role.USER));
      User changedUser = userRoleService.addMissingRolesIfNecessary(savedUser.id(), List.of(Role.ADMIN));

      // Then
      assertThat(changedUser.roles().size(), is(2));
      assertThat(changedUser.hasRole(Role.ADMIN), is(true));
      assertThat(changedUser.password(), is(password));
      assertThat(changedUser.username(), is(username));
   }
}