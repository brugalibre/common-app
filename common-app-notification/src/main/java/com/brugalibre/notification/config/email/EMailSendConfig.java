package com.brugalibre.notification.config.email;

import com.brugalibre.notification.config.ApiKeyConfig;

import java.util.function.Supplier;


public class EMailSendConfig {
   private ApiKeyConfig apiKeyConfig;
   private String smtpHost;
   private int smtpPort;
   private int smptSocketFactoryPort;
   private String smptSocketFactoryClass;
   private String username;
   private String sender;

   public EMailSendConfig() {
      this.apiKeyConfig = new ApiKeyConfig(""::toCharArray);
   }

   public void setApiKeyConfig(ApiKeyConfig apiKeyConfig) {
      this.apiKeyConfig = apiKeyConfig;
   }

   public String getSender() {
      return sender;
   }

   public void setSender(String sender) {
      this.sender = sender;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getSmptSocketFactoryClass() {
      return smptSocketFactoryClass;
   }

   public void setSmptSocketFactoryClass(String smptSocketFactoryClass) {
      this.smptSocketFactoryClass = smptSocketFactoryClass;
   }

   public ApiKeyConfig getApiKeyConfig() {
      return apiKeyConfig;
   }

   public void setApiKeyProvider(Supplier<char[]> apiKeyConfig) {
      this.apiKeyConfig = new ApiKeyConfig(apiKeyConfig);
   }

   public String getSmtpHost() {
      return smtpHost;
   }

   public void setSmtpHost(String smtpHost) {
      this.smtpHost = smtpHost;
   }

   public int getSmtpPort() {
      return smtpPort;
   }

   public void setSmtpPort(int smtpPort) {
      this.smtpPort = smtpPort;
   }

   public int getSmptSocketFactoryPort() {
      return smptSocketFactoryPort;
   }

   public void setSmptSocketFactoryPort(int smptSocketFactoryPort) {
      this.smptSocketFactoryPort = smptSocketFactoryPort;
   }

   public Supplier<char[]> getApiKeyProvider() {
      return apiKeyConfig.getApiKeyProvider();
   }
    /*
        /*
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
     */

}
