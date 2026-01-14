package com.brugalibre.notification.config;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.config.email.EMailSendConfig;
import com.brugalibre.notification.config.sms.SmsSendConfig;
import com.brugalibre.util.config.yml.YmlConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class AlertSendConfig implements YmlConfig {
   private SmsSendConfig smsSendConfig;
   private EMailSendConfig eMailSendConfig;

   private List<String> onApplicationErrorReceivers;
   private AlertType onApplicationErrorAlertType;

   public AlertSendConfig() {
      this.onApplicationErrorAlertType = AlertType.CLICK_SEND_SMS;
      this.eMailSendConfig = new EMailSendConfig();
      this.smsSendConfig = new SmsSendConfig();
   }

   public void seteMailSendConfig(EMailSendConfig eMailSendConfig) {
      this.eMailSendConfig = eMailSendConfig;
   }

   public SmsSendConfig getSmsSendConfig() {
      return smsSendConfig;
   }

   public void setSmsSendConfig(SmsSendConfig smsSendConfig) {
      this.smsSendConfig = smsSendConfig;
   }

   public AlertType getOnApplicationErrorAlertType() {
      return onApplicationErrorAlertType;
   }

   public void setOnApplicationErrorAlertType(AlertType onApplicationErrorAlertType) {
      this.onApplicationErrorAlertType = onApplicationErrorAlertType;
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

   public void setSmsApiKeyProvider(Supplier<char[]> smsApiKeyProvider) {
      this.smsSendConfig.setApiKeyProvider(smsApiKeyProvider);
   }

   public void setEMailApiKeyProvider(Supplier<char[]> eMailApiKeyProvider) {
      this.eMailSendConfig.setApiKeyProvider(eMailApiKeyProvider);
   }

   public EMailSendConfig getEMailSendConfig() {
      return eMailSendConfig;
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
