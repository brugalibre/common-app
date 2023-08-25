package com.brugalibre.domain.user.model.change;


import com.brugalibre.domain.user.model.User;

public interface UserChangedNotifier {

   /**
    * Registers the given {@link UserChangedObserver} which will be notified as soon as a {@link User} has changed
    *
    * @param userChangedObserver the {@link UserChangedObserver} to add
    */
   void addUserRegisteredObserver(UserChangedObserver userChangedObserver);

   /**
    * Unreegisters the given {@link UserChangedObserver}
    *
    * @param userChangedObserver the {@link UserChangedObserver} to remove
    */
   void removeUserRegisteredObserver(UserChangedObserver userChangedObserver);
}
