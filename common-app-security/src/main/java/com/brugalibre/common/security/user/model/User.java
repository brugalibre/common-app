package com.brugalibre.common.security.user.model;


import com.brugalibre.persistence.user.Role;
import com.brugalibre.persistence.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class User extends org.springframework.security.core.userdetails.User {

   private String id;
   private final String phoneNr;

   public User(UserEntity userEntity) {
      super(userEntity.getUsername(), userEntity.getPassword(), mapRoles2GrantedAuthorities(userEntity.getRoles()));
      this.id = userEntity.getId();
      this.phoneNr = userEntity.getPhoneNr();
   }

   public User(String username, String password, String phoneNr, List<Role> roles) {
      super(username, password, mapRoles2GrantedAuthorities(roles));
      this.phoneNr = phoneNr;
   }

   public String getId() {
      return id;
   }

   public String getPhoneNr() {
      return phoneNr;
   }

   /**
    * Returns a list of {@link Role}s from the {@link GrantedAuthority} values of this {@link User}
    *
    * @return a list of {@link Role}s from the {@link GrantedAuthority} values of this {@link User}
    */
   public List<Role> getRoles() {
      return this.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .map(Role::valueOf)
              .toList();
   }

   /**
    * Returns a list of {@link Role}s from the given {@link String} values
    *
    * @param roles the roles as {@link String} values
    * @return a list of {@link Role}s from the given {@link GrantedAuthority} values
    */
   public static List<Role> toRoles(List<String> roles) {
      return roles.stream()
              .map(Role::valueOf)
              .toList();
   }

   /**
    * Returns a list of {@link SimpleGrantedAuthority}s from the given {@link Role} values
    *
    * @param roles the roles as {@link Role} values
    * @return a list of {@link SimpleGrantedAuthority}s from the given {@link Role} values
    */
   public static List<SimpleGrantedAuthority> mapRoles2GrantedAuthorities(List<Role> roles) {
      return roles.stream()
              .map(Enum::name)
              .map(SimpleGrantedAuthority::new)
              .toList();
   }
}
