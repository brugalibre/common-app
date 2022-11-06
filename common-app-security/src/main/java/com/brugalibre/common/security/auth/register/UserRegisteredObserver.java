package com.brugalibre.common.security.auth.register;

import com.brugalibre.common.security.user.model.User;

/**
 * The {@link UserRegisteredObserver} can be implemented by anyone who likes to be informed about any registration of a {@link User}
 */
public interface UserRegisteredObserver {

   /**
    * Notifies that a {@link User} has been registered
    *
    * @param userRegisteredEvent the {@link UserRegisteredEvent}
    */
   void userRegistered(UserRegisteredEvent userRegisteredEvent);
}
