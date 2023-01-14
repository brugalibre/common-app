package com.brugalibre.util.config.yml;

public interface YmlConfig {

   /**
    * Refreshes this {@link YmlConfig} according to the underlying yaml-file and return
    * an updated instance of itself
    *
    * @return an updated instance of itself
    */
   YmlConfig refresh();
}
