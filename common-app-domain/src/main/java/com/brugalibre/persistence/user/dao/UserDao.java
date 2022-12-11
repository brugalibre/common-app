package com.brugalibre.persistence.user.dao;

import com.brugalibre.persistence.user.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserDao extends CrudRepository<UserEntity, String> {

   /**
    * Finds an optional {@link UserEntity} by its username
    *
    * @param username the username
    * @return an {@link Optional} of an {@link UserEntity}
    */
   Optional<UserEntity> findByUsername(String username);

   /**
    * Updates the phone-number for the user with the given username
    *
    * @param newPhoneNumber the new phone-number
    * @param username       the users-name
    */
   @Transactional
   @Modifying(flushAutomatically = true, clearAutomatically = true)
   @Query("UPDATE UserEntity user SET user.phoneNr = ?1 WHERE user.username = ?2")
   void updatePhoneNr(String newPhoneNumber, String username);
}
