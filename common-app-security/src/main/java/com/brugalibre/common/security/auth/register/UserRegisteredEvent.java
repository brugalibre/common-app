package com.brugalibre.common.security.auth.register;

import com.brugalibre.domain.user.model.User;

/**
 * The {@link UserRegisteredEvent} is an event which is fired everytime a new {@link User} is successfully registered
 *
 * @param userId   the technical id of the user
 * @param username the username
 * @param phoneNr  the phoneNr
 * @param password the password as char array
 */
public record UserRegisteredEvent(String userId, String username, String phoneNr, char[] password) {
   /**
    * Creates a new {@link UserRegisteredEvent} for the given registered {@link User}
    *
    * @param user the new registered User
    * @return a {@link UserRegisteredEvent}
    */
   public static UserRegisteredEvent of(User user, char[] password) {
      return new UserRegisteredEvent(user.getId(), user.username(), user.getMobilePhone().getPhoneNr(), password);
   }
}
