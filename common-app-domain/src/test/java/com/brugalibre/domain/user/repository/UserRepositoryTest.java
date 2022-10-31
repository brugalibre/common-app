package com.brugalibre.domain.user.repository;

import com.brugalibre.common.domain.config.CommonAppPersistenceConfig;
import com.brugalibre.domain.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = CommonAppPersistenceConfig.class)
class UserRepositoryTest {

   @Autowired
   private UserRepository userRepository;

   @Test
   void testCreateUserEntityWithPhoneNumber() {
      // Given
      String phoneNr = "+41 (78) 123 45 67";
      String expectedPhoneNur = "0041781234567";
      User user = new User(UUID.randomUUID().toString(), "jack", "def", phoneNr);

      // When
      User savedUser = userRepository.save(user);

      // Then
      assertThat(savedUser.phoneNr(), is(expectedPhoneNur));
   }

   @Test
   void testCreateUserEntityWithoutPhoneNumber() {
      // Given
      User user = new User(UUID.randomUUID().toString(), "peter", "abc", "");

      // When
      User savedUser = userRepository.save(user);

      // Then
      assertThat(savedUser.phoneNr(), is(""));
   }

   @Test
   void testCreateUserEntityWithNullPhoneNumber() {
      // Given
      User user = new User(UUID.randomUUID().toString(), "peterpan", "abc", null);

      // When
      User savedUser = userRepository.save(user);

      // Then
      assertThat(savedUser.phoneNr(), is(nullValue()));
   }
}