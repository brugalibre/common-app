package com.brugalibre.common.rest.api;

import com.brugalibre.common.domain.app.config.TestCommonAppPersistenceConfig;
import com.brugalibre.common.rest.model.ChangeUserRequest;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TestCommonAppPersistenceConfig.class)
class UserControllerTest {

   @Autowired
   private UserController userController;

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
      String userPhoneNr = "0712223344";
      String newPhoneNr = "0714445566";
      String password = "1234";
      userRepository.save(User.of(username, password, userPhoneNr));

      // When
      ChangeUserRequest changeUserRequest = new ChangeUserRequest(username, newPhoneNr);
      userController.changeUser(changeUserRequest);

      // Then
      List<User> users = userRepository.getAll();
      assertThat(users.size(), is(1));
      assertThat(users.get(0).username(), is(username));
      assertThat(users.get(0).phoneNr(), is(newPhoneNr));
   }

   @Test
   void createAndChangeUser_InvalidPhoneNr() {
      // Given
      String username = "hans";
      String userPhoneNr = "0712223344";
      String newPhoneNr = "asdfas";
      String password = "1234";
      userRepository.save(User.of(username, password, userPhoneNr));

      // When
      ChangeUserRequest changeUserRequest = new ChangeUserRequest(username, newPhoneNr);
      Assertions.assertThrows(IllegalArgumentException.class, () -> userController.changeUser(changeUserRequest));

      // Then, assert throws
   }
}