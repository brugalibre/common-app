package com.brugalibre.domain.user.service.userrole;

import com.brugalibre.persistence.user.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserRoleConfig {

   public static final String PATH_TO_CONFIGURED_USER_ROLES = "config/user-role-config.yml";
   private List<UserRoleConfigEntry> userRoleConfigEntries;

   public List<UserRoleConfigEntry> getUserRoleConfigEntries() {
      return userRoleConfigEntries;
   }

   public void setUserRoleConfigEntries(List<UserRoleConfigEntry> userRoleConfigEntries) {
      this.userRoleConfigEntries = userRoleConfigEntries;
   }

   public boolean contains(String username) {
      return userRoleConfigEntries.stream()
              .anyMatch(userRoleConfigEntry -> userRoleConfigEntry.getUsername().equals(username));
   }

   public List<Role> getRoles(String username) {
      return userRoleConfigEntries.stream()
              .filter(userRoleConfigEntry -> userRoleConfigEntry.getUsername().equals(username))
              .map(UserRoleConfigEntry::getRoles)
              .flatMap(Collection::stream)
              .collect(Collectors.toList());
   }

   @Override
   public String toString() {
      return "UserRoleConfig{" +
              "userRoleConfigEntries=" + userRoleConfigEntries +
              '}';
   }
}
