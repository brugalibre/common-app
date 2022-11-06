package com.brugalibre.common.security.auth.login;

import com.brugalibre.common.security.user.model.User;

import static java.util.Objects.nonNull;

/**
 * The {@link UserLoggedInEvent} is an event which is fired everytime a new {@link User} is successfully logged in
 *
 * @param userId   the technical id of the user
 * @param username the username
 * @param password the password as char array
 */
public record UserLoggedInEvent(String userId, String username, char[] password) {
   /**
    * Creates a new {@link UserLoggedInEvent} for the given logged in {@link User}
    *
    * @param user the new logged-in User
    * @return a {@link UserLoggedInEvent}
    */
   public static UserLoggedInEvent of(User user) {
      char[] password = nonNull(user.getPassword()) ? user.getPassword().toCharArray() : new char[]{};
      return new UserLoggedInEvent(user.getId(), user.getUsername(), password);
   }
}
