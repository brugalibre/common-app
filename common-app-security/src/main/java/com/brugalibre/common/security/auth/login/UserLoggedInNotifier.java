package com.brugalibre.common.security.auth.login;

import com.brugalibre.common.security.user.model.User;

public interface UserLoggedInNotifier {

   /**
    * Registers the given {@link UserLoggedInObserver} which will be notified as soon as a {@link User} is logged in
    *
    * @param userLoggedInObserver the {@link UserLoggedInObserver} to add
    */
   void addUserRegisteredObserver(UserLoggedInObserver userLoggedInObserver);
}
