package com.brugalibre.domain.user.model.userrole;

import com.brugalibre.persistence.user.Role;

import java.util.ArrayList;
import java.util.List;

public class UserRoleConfigEntry {
   private String username;
   private List<Role> roles;

   public UserRoleConfigEntry() {
      this.roles = new ArrayList<>();
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = roles;
   }

   @Override
   public String toString() {
      return "UserRoleConfigEntry{" +
              "username='" + username + '\'' +
              ", roles='" + roles + '\'' +
              '}';
   }
}
