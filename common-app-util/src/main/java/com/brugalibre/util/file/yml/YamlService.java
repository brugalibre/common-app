package com.brugalibre.util.file.yml;

import com.brugalibre.util.file.FileUtil;
import com.brugalibre.util.config.yml.YmlConfig;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.representer.Representer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

public class YamlService {

   private final boolean ignoreMissingFile;

   public YamlService(boolean ignoreMissingFile) {
      this.ignoreMissingFile = ignoreMissingFile;
   }

   public YamlService() {
      ignoreMissingFile = false;
   }

   /**
    * Loads the given yml file and create a new class with the content
    * of the read file
    *
    * @param ymlFile the file
    * @param clazz   type of the class to create
    * @return a new instance of the given class, with the content of the given yml-file
    */
   public <T extends YmlConfig> T readYaml(String ymlFile, Class<T> clazz) {
      Representer representer = createRepresenter();
      Yaml yaml = new Yaml(new Constructor(clazz, new LoaderOptions()), representer);
      try (InputStream inputStream = FileUtil.getInputStream(ymlFile)) {
         T ymlConfig = yaml.load(inputStream);
         ymlConfig.setConfigFile(ymlFile);
         return ymlConfig;
      } catch (FileNotFoundException e) {
         if (ignoreMissingFile) {
            return createEmptyConfig(clazz);
         }
         throw new IllegalStateException(e);
      } catch (IOException e) {
         throw new IllegalStateException(e);
      }
   }

   private <T extends YmlConfig> T createEmptyConfig(Class<T> clazz) {
      try {
         return clazz.getConstructor().newInstance();
      } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
         throw new IllegalStateException("Not possible to create empty config instance!", e);
      }
   }

   /**
    * Loads the given yml file and create a new class with the content
    * of the read file
    * If there is no file, then a new instance of the given class is created, using its default constructor
    *
    * @param ymlFile the file
    * @param clazz   type of the class to create
    * @return a new instance of the given class, with the content of the given yml-file
    */
   public <T extends YmlConfig> T readYamlIgnoreMissingFile(String ymlFile, Class<T> clazz) {
      try {
         return readYaml(ymlFile, clazz);
      } catch (Exception e) {
         try {
            return clazz.getDeclaredConstructor().newInstance();
         } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            throw new IllegalStateException(e);
         }
      }
   }

   private static Representer createRepresenter() {
      Representer representer = new Representer(new DumperOptions());
      representer.getPropertyUtils().setSkipMissingProperties(true);
      return representer;
   }
}

