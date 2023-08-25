package com.brugalibre.common.security.rest.api;

import com.brugalibre.common.rest.api.mobilephone.MobilePhoneController;
import com.brugalibre.common.rest.model.ChangeMobilePhoneRequest;
import com.brugalibre.common.security.auth.config.TestCommonAppSecurityConfig;
import com.brugalibre.common.security.auth.register.UserRegisteredEvent;
import com.brugalibre.common.security.auth.register.UserRegisteredObserver;
import com.brugalibre.common.security.rest.model.RegisterRequest;
import com.brugalibre.common.security.rest.service.UserRegisterService;
import com.brugalibre.common.security.user.repository.UserDetailsServiceImpl;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.persistence.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TestCommonAppSecurityConfig.class)
class AuthControllerTest {

   @Autowired
   private AuthController authController;

   @Autowired
   private MobilePhoneController mobilePhoneController;

   @Autowired
   private UserRegisterService userRegisterService;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private UserDetailsServiceImpl userDetailsService;

   @AfterEach
   public void cleanUp() {
      userRepository.deleteAll();
   }

   @Test
   void authenticateUser() {
      // Given
      String username = "peter";
      String userPhoneNr = "+41711234567";
      String expectedPhoneNr = userPhoneNr.replace("+", "00");
      String password = "1234";
      char[] clearedPassword = "0000".toCharArray();
      RegisterRequest registerRequest = new RegisterRequest(username, password, userPhoneNr, List.of("USER"));

      TestUserRegisteredObserver userRegisteredObserver = new TestUserRegisteredObserver();
      userRegisterService.addUserRegisteredObserver(userRegisteredObserver);

      // When
      authController.registerUser(registerRequest);

      // Then
      List<User> users = userRepository.getAll();
      assertThat(users.size(), is(1));
      assertThat(users.get(0).username(), is(username));
      assertThat(users.get(0).getMobilePhone().getPhoneNr(), is(expectedPhoneNr));
      org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) userDetailsService.loadUserByUsername(username);
      assertThat(securityUser.getAuthorities().size(), is(1));
      assertThat(new ArrayList<>(securityUser.getAuthorities()).get(0).getAuthority(), is(Role.USER.name()));

      assertThat(userRegisteredObserver.userRegisteredEvents.size(), is(1));
      UserRegisteredEvent userRegisteredEvent = userRegisteredObserver.userRegisteredEvents.get(0);
      assertThat(userRegisteredEvent.username(), is(username));
      assertThat(userRegisteredEvent.phoneNr(), is(expectedPhoneNr));
      // make sure the observer was called with the right password and also that this password was cleared in the end
      assertThat(userRegisteredEvent.password(), is(clearedPassword));
      assertThat(userRegisteredObserver.actualPasswords.get(0), is(password));

      userRegisterService.removeUserRegisteredObserver(userRegisteredObserver);
   }

   @Test
   void createAndChangeUser() {
      // Given
      String username = "hans";
      String userPhoneNr = "0712223344";
      String newPhoneNr = "0714445566";
      String password = "1234";
      RegisterRequest registerRequest = new RegisterRequest(username, password, userPhoneNr, List.of("USER"));

      // When
      authController.registerUser(registerRequest);
      User user = userRepository.getAll().get(0);
      ChangeMobilePhoneRequest changeUserRequest = new ChangeMobilePhoneRequest(user.id(), newPhoneNr);
      mobilePhoneController.changeMobilePhone(changeUserRequest);

      // Then
      List<User> users = userRepository.getAll();
      assertThat(users.size(), is(1));
      assertThat(users.get(0).username(), is(username));
      assertThat(users.get(0).getMobilePhone().getPhoneNr(), is(newPhoneNr));
      org.springframework.security.core.userdetails.User securityUser = (org.springframework.security.core.userdetails.User) userDetailsService.loadUserByUsername(username);
      assertThat(securityUser.getAuthorities().size(), is(1));
      assertThat(new ArrayList<>(securityUser.getAuthorities()).get(0).getAuthority(), is(Role.USER.name()));
   }

   @Test
   void registerUserWithExceptionFromObserver() {
      // Given
      String username = "peter";
      String userPhoneNr = "0712223344";
      String password = "4321";
      RegisterRequest registerRequest = new RegisterRequest(username, password, userPhoneNr, List.of("USER"));
      ExceptionThrowingObserver exceptionThrowingObserver = new ExceptionThrowingObserver();
      userRegisterService.addUserRegisteredObserver(exceptionThrowingObserver);

      // When
      try {
         authController.registerUser(registerRequest);
      } catch (IllegalStateException e) {
         //ignore
      }

      // Then
      List<User> users = userRepository.getAll();
      assertThat(users.size(), is(0));
      userRegisterService.removeUserRegisteredObserver(exceptionThrowingObserver);
   }

   public static class TestUserRegisteredObserver implements UserRegisteredObserver {
      private final List<UserRegisteredEvent> userRegisteredEvents;
      private final List<String> actualPasswords;

      public TestUserRegisteredObserver() {
         this.userRegisteredEvents = new ArrayList<>();
         this.actualPasswords = new ArrayList<>();
      }

      @Override
      public void userRegistered(UserRegisteredEvent userRegisteredEvent) {
         userRegisteredEvents.add(userRegisteredEvent);
         actualPasswords.add(String.valueOf(userRegisteredEvent.password()));
      }
   }

   private static class ExceptionThrowingObserver implements UserRegisteredObserver {
      @Override
      public void userRegistered(UserRegisteredEvent userRegisteredEvent) {
         throw new IllegalStateException();
      }
   }
}