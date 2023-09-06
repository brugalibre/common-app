package com.brugalibre.notification.config;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.util.config.yml.YmlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class AlertSendConfig implements YmlConfig {
   private Supplier<char[]> apiKeyProvider;
   private String alertServiceName;
   private String username;
   private String originator;
   private int originatorMailId;
   private List<AlertType> alertTypes;
   private List<String> onApplicationErrorReceivers;

   public AlertSendConfig() {
      this.apiKeyProvider = ""::toCharArray;
      this.alertTypes = List.of(AlertType.SMS);// default is sms
   }

   public int getOriginatorMailId() {
      return originatorMailId;
   }

   public void setOriginatorMailId(int originatorMailId) {
      this.originatorMailId = originatorMailId;
   }

   public List<AlertType> getAlertTypes() {
      return alertTypes;
   }

   public void setAlertTypes(List<AlertType> alertTypes) {
      this.alertTypes = alertTypes;
   }

   public String getUsername() {
      return username;
   }

   public String getAlertServiceName() {
      return alertServiceName;
   }

   public void setAlertServiceName(String alertServiceName) {
      this.alertServiceName = alertServiceName;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getApiKey() {
      return String.valueOf(apiKeyProvider.get());
   }

   public String getOriginator() {
      return originator;
   }

   public void setOriginator(String originator) {
      this.originator = originator;
   }

   public List<String> getOnApplicationErrorReceivers() {
      if (isNull(onApplicationErrorReceivers)) {
         this.onApplicationErrorReceivers = new ArrayList<>();
      }
      return onApplicationErrorReceivers;
   }

   public void setOnApplicationErrorReceivers(List<String> onApplicationErrorReceivers) {
      this.onApplicationErrorReceivers = onApplicationErrorReceivers;
   }

   public void setApiKeyProvider(Supplier<char[]> apiKeyProvider) {
      this.apiKeyProvider = apiKeyProvider;
   }

   /**
    * @return the very same config instance - no refresh implemented!
    */
   @Override
   public YmlConfig refresh() {
      return this;
   }

   @Override
   public void setConfigFile(String alertSendConfigFile) {
      // unused
   }
}
