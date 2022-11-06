package com.brugalibre.domain.user.model;

import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.persistence.user.UserEntity;

import java.util.Objects;
import java.util.UUID;

public record User(String id, String username, String password, String phoneNr) implements DomainModel {
   @Override
   public String getId() {
      return id;
   }

   /**
    * Creates a new instance of a {@link User} from the given {@link UserEntity}
    *
    * @param userEntity the {@link UserEntity}
    */
   public static User of(UserEntity userEntity) {
      return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getPhoneNr());
   }

   /**
    * Creates a new instance of a {@link User} with the given parameters.
    *
    * @param id       the users id
    * @param username the users name
    * @param password the users password
    * @param phoneNr  the users phone number
    * @return a new instance of a {@link User}
    */
   public static User of(String id, String username, String password, String phoneNr) {
      return new User(id, username, password, phoneNr);
   }

   /**
    * Creates a new instance of a {@link User} with the given parameters.
    * The id will be <code>null</code>
    *
    * @param username the users name
    * @param password the users password
    * @param phoneNr  the users phone number
    * @return a new instance of a {@link User}
    */
   public static User of(String username, String password, String phoneNr) {
      return new User(null, username, password, phoneNr);
   }

   /**
    * Creates a new instance of a {@link User} with the given parameters.
    * The id will be <code>null</code>
    *
    * @param username the users name
    * @param password the users password
    * @return a new instance of a {@link User}
    */
   public static User of(String username, String password) {
      return new User(null, username, password, null);
   }

   /**
    * Creates a new instance of a {@link User} only with the given username.
    * Password will be created with random values. The id will be <code>null</code>
    *
    * @param username the users name
    * @return a new instance of a {@link User}
    */
   public static User of(String username) {
      return new User(null, username, UUID.randomUUID().toString(), null);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return Objects.equals(username, user.username);
   }

   @Override
   public int hashCode() {
      return Objects.hash(username);
   }

   @Override
   public String toString() {
      return "User{" +
              "id='" + id + '\'' +
              ", username='" + "PROTECTED" + '\'' +
              ", password='" + "PROTECTED" + '\'' +
              ", phoneNr='" + phoneNr + '\'' +
              '}';
   }
}
