package com.brugalibre.domain.user.repository;

import com.brugalibre.common.domain.app.config.TestCommonAppPersistenceConfig;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.domain.contactpoint.mobilephone.repository.MobilePhoneRepository;
import com.brugalibre.domain.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TestCommonAppPersistenceConfig.class)
class UserRepositoryTest {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private MobilePhoneRepository mobilePhoneRepository;

   @AfterEach
   private void cleanUp() {
      userRepository.deleteAll();
   }

   @Test
   void testCreateUserEntityWithPhoneNumber() {
      // Given
      String phoneNr = "+41 (78) 123 45 67";
      String expectedPhoneNur = "0041781234567";
      String username = "jack1";
      User user = User.of(username, "def", MobilePhone.of(phoneNr));

      // When
      User savedUser = userRepository.save(user);

      // Then
      assertThat(savedUser.username(), is(username));
      assertThat(savedUser.getMobilePhone().getPhoneNr(), is(expectedPhoneNur));
   }

   @Test
   void testCreateUserEntityWithPhoneNumberAndUpdatePhoneNr() {
      // Given
      String phoneNr = "+41 (78) 123 45 67";
      String newPhoneNr = "+41 (78) 123 45 99";
      String expectedCreatedPhoneNr = "0041781234567";
      String expectedChangedPhoneNr = "0041781234599";
      String username = "jack2";
      User user = User.of(username, "def", MobilePhone.of(phoneNr));

      // When
      User savedUser = userRepository.save(user);

      // Then
      assertThat(savedUser.username(), is(username));
      MobilePhone mobilePhone = savedUser.getMobilePhone();
      assertThat(mobilePhone.getPhoneNr(), is(expectedCreatedPhoneNr));

      // When
      mobilePhoneRepository.updatePhoneNr(mobilePhone.getUserId(), newPhoneNr);
      MobilePhone updatedMobilePhone = mobilePhoneRepository.getById(mobilePhone.getId());

      // Then
      assertThat(updatedMobilePhone.getPhoneNr(), is(expectedChangedPhoneNr));
   }

   @Test
   void testCreateUserEntityWithNullPhoneNumber() {
      // Given
      User user = User.of("peterpan", "abc");

      // When
      User savedUser = userRepository.save(user);

      // Then
      assertThat(savedUser.getMobilePhone(), is(nullValue()));
   }
}