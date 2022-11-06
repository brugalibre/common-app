package com.brugalibre.common.security.auth.login;

import com.brugalibre.common.security.user.model.User;

/**
 * The {@link UserLoggedInObserver} can be implemented by anyone who likes to be informed about a log-in of a {@link User}
 */
public interface UserLoggedInObserver {

   /**
    * Notifies that a {@link User} has logged in
    *
    * @param userLoggedInEvent the {@link UserLoggedInEvent}
    */
   void userLoggedIn(UserLoggedInEvent userLoggedInEvent);
}
