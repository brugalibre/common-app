package com.brugalibre.notification.config.sms;

import com.brugalibre.notification.config.ApiKeyConfig;

import java.util.function.Supplier;

public class SmsSendConfig {
   private ApiKeyConfig apiKeyConfig;
   private String alertServiceName;
   private String username;
   private String originator;

   public SmsSendConfig() {
      this.apiKeyConfig = new ApiKeyConfig(""::toCharArray);
   }

   public void setApiKeyConfig(ApiKeyConfig apiKeyConfig) {
      this.apiKeyConfig = apiKeyConfig;
   }

   public Supplier<char[]> getApiKeyProvider() {
      return apiKeyConfig.getApiKeyProvider();
   }

   public void setApiKeyProvider(Supplier<char[]> apiKeyProvider) {
      this.apiKeyConfig = new ApiKeyConfig(apiKeyProvider);
   }

   public String getAlertServiceName() {
      return alertServiceName;
   }

   public void setAlertServiceName(String alertServiceName) {
      this.alertServiceName = alertServiceName;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getOriginator() {
      return originator;
   }

   public void setOriginator(String originator) {
      this.originator = originator;
   }
}
