package com.brugalibre.common.security.auth.register;

import com.brugalibre.common.security.user.model.User;

/**
 * The {@link UserRegisteredObserver} can be implemented by any one whom likes to be informed about any registration of a {@link User}
 */
public interface UserRegisteredObserver {

   /**
    * Notifies that a {@link User} has been registered
    *
    * @param userRegisteredEvent the {@link UserRegisteredEvent}
    */
   void userRegistered(UserRegisteredEvent userRegisteredEvent);
}
