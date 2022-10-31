package com.brugalibre.common.security.user.model;

import org.springframework.security.core.GrantedAuthority;

public record GrantedAuthorityImpl(String authority) implements GrantedAuthority {

   @Override
   public String getAuthority() {
      return authority;
   }

   @Override
   public String toString() {
      return authority;
   }
}
