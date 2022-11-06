package com.brugalibre.common.security.user.service;


import com.brugalibre.domain.user.model.User;

/**
 * Interface for retrieving the currently logged in {@link User}
 */
public interface IUserProvider {
   /**
    * @return the currently logged in {@link User}
    */
   User getCurrentUser();

   /**
    * @return the id of the currently logged in {@link User}
    */
   String getCurrentUserId();
}
