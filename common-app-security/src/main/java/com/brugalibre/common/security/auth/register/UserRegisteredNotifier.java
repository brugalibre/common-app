package com.brugalibre.common.security.auth.register;

import com.brugalibre.common.security.user.model.User;

public interface UserRegisteredNotifier {

   /**
    * Registers the given {@link UserRegisteredObserver} which will be notified as soon as a {@link User} is registered
    *
    * @param userRegisteredObserver the {@link UserRegisteredObserver} to add
    */
   void addUserRegisteredObserver(UserRegisteredObserver userRegisteredObserver);

   /**
    * Unreegisters the given {@link UserRegisteredObserver}
    *
    * @param userRegisteredObserver the {@link UserRegisteredObserver} to remove
    */
   void removeUserRegisteredObserver(UserRegisteredObserver userRegisteredObserver);
}
