package com.brugalibre.notification.config;

import com.brugalibre.util.file.yml.YamlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DefaultAlertSendConfigProviderImpl implements AlertSendConfigProvider {

   private final String smsApiKey;
   private final String eMailApiKey;
   private final String alertConfigFile;
   private final YamlService yamlService;

   @Autowired
   public DefaultAlertSendConfigProviderImpl(@Value("${application.notification.notificationConfigFile}") String alertConfigFile,
                                             @Value("${application.notification.smsApiKey}") String smsApiKey,
                                             @Value("${application.notification.eMailApiKey}") String eMailApiKey) {
      this.alertConfigFile = alertConfigFile;
      this.smsApiKey = smsApiKey;
      this.eMailApiKey = eMailApiKey;
      this.yamlService = new YamlService();
   }

   @Override
   public AlertSendConfig getAlertSendConfig() {
      AlertSendConfig alertSendConfig = yamlService.readYaml(alertConfigFile, AlertSendConfig.class);
      alertSendConfig.setSmsApiKeyProvider(smsApiKey::toCharArray);
      alertSendConfig.setEMailApiKeyProvider(eMailApiKey::toCharArray);
      return alertSendConfig;
   }
}
