package com.brugalibre.persistence.user;

import java.util.List;

public enum Role {
   USER,
   ADMIN;

   /*
    * Spring auto insert the prefix when an ant-matcher for a specific role is configured. So we need to add this
    * prefix internally in order to match a certain user with its role
    */
   public static final String ROLE_PREFIX = "ROLE_";

   /**
    * Returns a list of {@link Role}s from the given {@link String} values>
    *
    * @param roles the roles as {@link String} values
    * @return a list of {@link Role}s from the given {@link String} values>
    */
   public static List<Role> toRoles(List<String> roles) {
      return roles.stream()
              .map(role -> role.replace(ROLE_PREFIX, ""))
              .map(Role::valueOf)
              .toList();
   }


}
