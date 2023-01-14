package com.brugalibre.domain.file.yml.service;

import com.brugalibre.util.config.yml.YmlConfig;

import java.util.ArrayList;
import java.util.List;

public class TestYmlConfig implements YmlConfig {
   private final List<String> someList;
   private final boolean booleanValue;

   public TestYmlConfig() {
      this.booleanValue = false;
      this.someList = new ArrayList<>();
   }

   @Override
   public YmlConfig refresh() {
      return this;
   }

   public List<String> getSomeList() {
      return someList;
   }

   public boolean isBooleanValue() {
      return booleanValue;
   }
}
