package com.brugalibre.common.security.user.model;


import com.brugalibre.persistence.user.Role;
import com.brugalibre.persistence.user.UserEntity;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import static com.brugalibre.persistence.user.Role.ROLE_PREFIX;

public class User extends org.springframework.security.core.userdetails.User {

   private String id;

   public User() {
      super("null", "null", List.of());
   }

   public User(UserEntity userEntity) {
      super(userEntity.getUsername(), userEntity.getPassword(), mapRoles2GrantedAuthorities(userEntity.getRoles()));
      this.id = userEntity.getId();
   }

   public User(String username, String password, List<Role> roles) {
      super(username, password, mapRoles2GrantedAuthorities(roles));
   }

   public String getId() {
      return id;
   }

   /**
    * Returns a list of {@link Role}s from the given {@link GrantedAuthority} values>
    *
    * @param authorities the roles as {@link GrantedAuthority} values
    * @return a list of {@link Role}s from the given {@link GrantedAuthority} values>
    */
   public static List<Role> toRoles(Collection<GrantedAuthority> authorities) {
      return Role.toRoles(authorities.stream()
              .map(GrantedAuthority::getAuthority)
              .toList());
   }

   /**
    * Returns a list of {@link String}s from the given {@link GrantedAuthority} values>
    *
    * @param roles the roles as {@link String} values
    * @return a list of {@link Role}s from the given {@link String} values>
    */
   public static List<String> mapAuthorities2Roles(Collection<? extends GrantedAuthority> roles) {
      return roles.stream()
              .map(GrantedAuthority::getAuthority)
              .map(role -> ROLE_PREFIX + role)
              .toList();
   }

   /**
    * Returns a list of {@link GrantedAuthorityImpl}s from the given {@link Role} values>
    *
    * @param roles the roles as {@link Role} values
    * @return a list of {@link GrantedAuthorityImpl}s from the given {@link Role} values>
    */
   public static List<GrantedAuthorityImpl> mapRoles2GrantedAuthorities(List<Role> roles) {
      return roles.stream()
              .map(Enum::name)
              .map(role -> ROLE_PREFIX + role)
              .map(GrantedAuthorityImpl::new)
              .toList();
   }
}
