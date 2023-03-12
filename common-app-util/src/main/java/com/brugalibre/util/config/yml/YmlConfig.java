package com.brugalibre.util.config.yml;

public interface YmlConfig {

   /**
    * Refreshes this {@link YmlConfig} according to the underlying yaml-file and return
    * an updated instance of itself
    *
    * @return an updated instance of itself
    */
   YmlConfig refresh();

   /**
    * Defines the given path to a config-file as the configuration source for this {@link YmlConfig}
    *
    * @param configFile the path either full qualified or relative path
    */
   void setConfigFile(String configFile);
}
