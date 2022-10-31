package com.brugalibre.persistence.user;

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
}
