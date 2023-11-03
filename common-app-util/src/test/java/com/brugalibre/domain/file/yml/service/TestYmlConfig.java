package com.brugalibre.domain.file.yml.service;

import com.brugalibre.util.config.yml.YmlConfig;

import java.util.ArrayList;
import java.util.List;

public class TestYmlConfig implements YmlConfig {
   private List<String> someList;
   private boolean booleanValue;

   public TestYmlConfig() {
      this.booleanValue = false;
      this.someList = new ArrayList<>();
   }

   @Override
   public YmlConfig refresh() {
      return this;
   }

   @Override
   public void setConfigFile(String configFile) {
      // ignore
   }

   public List<String> getSomeList() {
      return someList;
   }

   public boolean getBooleanValue() {
      return booleanValue;
   }

   public void setSomeList(List<String> someList) {
      this.someList = someList;
   }

   public void setBooleanValue(boolean booleanValue) {
      this.booleanValue = booleanValue;
   }
}
