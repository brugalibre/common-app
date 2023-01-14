package com.brugalibre.domain.file.yml.service;

import com.brugalibre.util.file.yml.YamlService;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.CoreMatchers.is;

class YamlServiceTest {

   @Test
   void readYamlWithoutConfigFileIgnore() {
      // Given
      YamlService yamlService = new YamlService(true);

      // When
      TestYmlConfig userRoleConfig = yamlService.readYaml("abcdef", TestYmlConfig.class);

      // Then
      MatcherAssert.assertThat(userRoleConfig.getSomeList().size(), is(0));
      MatcherAssert.assertThat(userRoleConfig.isBooleanValue(), is(false));
   }

   @Test
   void readYamlWithoutConfigFile_DontIgnore() {
      // Given
      YamlService yamlService = new YamlService(false);

      // When
      Executable exe = () -> yamlService.readYaml("abcdef", TestYmlConfig.class);

      // Then
      Assertions.assertThrows(IllegalStateException.class, exe);
   }
}