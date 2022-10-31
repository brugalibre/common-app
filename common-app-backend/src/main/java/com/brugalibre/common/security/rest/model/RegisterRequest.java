package com.brugalibre.common.security.rest.model;

import com.brugalibre.persistence.user.Role;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record RegisterRequest(String username, String password, List<String> roles) {
   public List<Role> map2Role() {
      return Role.toRoles(roles);
   }
}
