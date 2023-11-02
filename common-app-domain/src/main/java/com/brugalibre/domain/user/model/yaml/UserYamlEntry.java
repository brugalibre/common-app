package com.brugalibre.domain.user.model.yaml;

import com.brugalibre.persistence.user.Role;

import java.util.List;

public class UserYamlEntry {
   private String username;
   private String password;
   private List<Role> roles;
   private String phoneNr;

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = roles;
   }

   public String getPhoneNr() {
      return phoneNr;
   }

   public void setPhoneNr(String phoneNr) {
      this.phoneNr = phoneNr;
   }
}
