package com.brugalibre.domain.user.model.yaml;

import com.brugalibre.util.config.yml.YmlConfig;

import java.util.List;

public class UserYamlInput implements YmlConfig {
   private List<UserYamlEntry> userYamlEntries;

   public List<UserYamlEntry> getUserYamlEntries() {
      return userYamlEntries;
   }

   public void setUserYamlEntries(List<UserYamlEntry> userYamlEntries) {
      this.userYamlEntries = userYamlEntries;
   }

   @Override
   public YmlConfig refresh() {
      return this;
   }

   @Override
   public void setConfigFile(String s) {

   }
}