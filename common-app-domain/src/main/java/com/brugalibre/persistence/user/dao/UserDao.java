package com.brugalibre.persistence.user.dao;

import com.brugalibre.domain.contactpoint.ContactPointType;
import com.brugalibre.domain.contactpoint.model.ContactPoint;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.persistence.user.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

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
     * Finds a {@link UserEntity} which has a {@link ContactPoint} with type {@link ContactPointType#MOBILE_PHONE}
     * which has de given phone-nr
     *
     * @param phoneNr the phone-nr
     * @return an {@link Optional} containing the found {@link UserEntity} or an empty optional if there is none for the given phone-nr
     */
    @Query("SELECT cp.user FROM UserEntity user JOIN user.contactPoints cp " +
            "WHERE cp.contactPointType = MOBILE_PHONE " +
            "AND cp.phoneNr = ?1")
    Optional<UserEntity> findByPhoneNr(String phoneNr);

    /**
     * Changes the {@link User}s password
     *
     * @param newPassword the new password value to set
     * @param userId      the id of the User whose password has to be changed
     */
    @Modifying()
    @Query("UPDATE UserEntity user SET user.password = ?1 WHERE user.id = ?2")
    void setPassword(String newPassword, String userId);

    /**
     * Changes the {@link User}s username
     *
     * @param newUsername the new username value to set
     * @param userId      the id of the User whose name has to be changed
     */
    @Modifying()
    @Query("UPDATE UserEntity user SET user.username = ?1 WHERE user.id = ?2")
    void setUsername(String newUsername, String userId);
}
