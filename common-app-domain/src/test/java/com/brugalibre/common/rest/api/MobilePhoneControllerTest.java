package com.brugalibre.common.rest.api;

import com.brugalibre.common.domain.app.config.TestCommonAppPersistenceConfig;
import com.brugalibre.common.rest.api.mobilephone.MobilePhoneController;
import com.brugalibre.common.rest.model.ChangeMobilePhoneRequest;
import com.brugalibre.domain.contactpoint.mobilephone.exception.PhoneNrNotValidException;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TestCommonAppPersistenceConfig.class)
class MobilePhoneControllerTest {

   @Autowired
   private MobilePhoneController mobilePhoneController;

   @Autowired
   private UserRepository userRepository;

   @AfterEach
   public void cleanUp() {
      userRepository.deleteAll();
   }

   @Test
   void createAndChangeUser() {
      // Given
      String username = "hans";
      String userPhoneNr = "0712223311";
      String newPhoneNr = "0714445566";
      String password = "1234";
      User user = userRepository.save(User.of(username, password, MobilePhone.of(userPhoneNr)));

      // When
      ChangeMobilePhoneRequest changeMobilePhoneRequest = new ChangeMobilePhoneRequest(user.id(), newPhoneNr);
      mobilePhoneController.changeMobilePhone(changeMobilePhoneRequest);

      // Then
      User persistedUser = userRepository.getById(user.id());
      assertThat(persistedUser.username(), is(username));
      assertThat(persistedUser.getMobilePhone().getPhoneNr(), is(newPhoneNr));
   }

   @Test
   void createAndChangeUser_InvalidPhoneNr() {
      // Given
      String username = "hans-jörg";
      String userPhoneNr = "0712224321";
      String newPhoneNr = "asdfas";
      String password = "1234";
      User user = userRepository.save(User.of(username, password, MobilePhone.of(userPhoneNr)));

      // When
      ChangeMobilePhoneRequest changeMobilePhoneRequest = new ChangeMobilePhoneRequest(user.id(), newPhoneNr);
      Assertions.assertThrows(PhoneNrNotValidException.class, () -> mobilePhoneController.changeMobilePhone(changeMobilePhoneRequest));

      // Then, assert throws
   }
}