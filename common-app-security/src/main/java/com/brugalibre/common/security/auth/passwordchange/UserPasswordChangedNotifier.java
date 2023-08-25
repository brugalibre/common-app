package com.brugalibre.common.security.auth.passwordchange;

public interface UserPasswordChangedNotifier {

   /**
    * Registers the given {@link UserPasswordChangedObserver}
    *
    * @param userPasswordChangedObserver the {@link UserPasswordChangedObserver} to add
    */
   void addUserPasswordChangedObserver(UserPasswordChangedObserver userPasswordChangedObserver);

   /**
    * Unregisters the given {@link com.brugalibre.domain.user.model.change.UserChangedObserver}
    *
    * @param userPasswordChangedObserver the {@link UserPasswordChangedObserver} to remove
    */
   void removeUserPasswordChangedObserver(UserPasswordChangedObserver userPasswordChangedObserver);
}
