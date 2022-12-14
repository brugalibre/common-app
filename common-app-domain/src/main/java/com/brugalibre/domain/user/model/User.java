package com.brugalibre.domain.user.model;

import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.domain.contactpoint.mobilephone.mapper.MobilePhoneEntityMapperImpl;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.domain.contactpoint.model.ContactPoint;
import com.brugalibre.persistence.contactpoint.mobilephone.MobilePhoneEntity;
import com.brugalibre.persistence.user.Role;
import com.brugalibre.persistence.user.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public record User(String id, String username, String password,
                   List<ContactPoint> contactPoints, List<Role> roles) implements DomainModel {
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
      MobilePhoneEntityMapperImpl mobilePhoneEntityMapper = new MobilePhoneEntityMapperImpl();
      MobilePhone mobilePhone = mobilePhoneEntityMapper.map2DomainModel(getMobilePhoneEntity(userEntity));
      return new User(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(),
              List.of(mobilePhone), userEntity.getRoles());
   }

   /**
    * Creates a new instance of a {@link User} with the given parameters.
    *
    * @param id          the users id
    * @param username    the users name
    * @param password    the users password
    * @param mobilePhone the users {@link MobilePhone}
    * @return a new instance of a {@link User}
    */
   public static User of(String id, String username, String password, MobilePhone mobilePhone) {
      return new User(id, username, password, List.of(mobilePhone), getDefaultUserRoles());
   }

   /**
    * Creates a new instance of a {@link User} with the given parameters.
    * The id will be <code>null</code>
    *
    * @param username    the users name
    * @param password    the users password
    * @param mobilePhone the users {@link MobilePhone}
    * @return a new instance of a {@link User}
    */
   public static User of(String username, String password, MobilePhone mobilePhone) {
      return new User(null, username, password, List.of(mobilePhone), getDefaultUserRoles());
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
      return new User(null, username, password, List.of(), getDefaultUserRoles());
   }

   /**
    * Creates a new instance of a {@link User} only with the given username.
    * Password will be created with random values. The id will be <code>null</code>
    *
    * @param username the users name
    * @return a new instance of a {@link User}
    */
   public static User of(String username) {
      return new User(null, username, UUID.randomUUID().toString(), List.of(), getDefaultUserRoles());
   }

   private static List<Role> getDefaultUserRoles() {
      return List.of(Role.USER);
   }

   public MobilePhone getMobilePhone() {
      return contactPoints.stream()
              .filter(MobilePhone.class::isInstance)
              .map(MobilePhone.class::cast)
              .findFirst()
              .orElse(null);
   }

   private static MobilePhoneEntity getMobilePhoneEntity(UserEntity userEntity) {
      return userEntity.getContactPoints()
              .stream()
              .filter(MobilePhoneEntity.class::isInstance)
              .map(MobilePhoneEntity.class::cast)
              .findFirst()
              .orElse(null);
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
              ", contactPoints='" + contactPoints + '\'' +
              '}';
   }

   /**
    * Checks if this {@link User} has the given {@link Role} assigned
    *
    * @param role the {@link Role} to check
    * @return <code>true</code> if this {@link User} has the given {@link Role} assigned <code>false</code> if not
    */
   public boolean hasRole(Role role) {
      return roles.contains(role);
   }

   /**
    * Adds the given {@link Role} to this {@link User} and returns a new instance
    *
    * @param role the {@link Role} to add
    * @return a new instance of the changed {@link User}
    */
   public User addRole(Role role) {
      List<Role> newRoles = new ArrayList<>(this.roles);
      newRoles.add(role);
      return new User(this.id, this.username, this.password, this.contactPoints, newRoles);
   }
}
