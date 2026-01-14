package com.brugalibre.notification.config;

import java.util.function.Supplier;

public class ApiKeyConfig {
   private Supplier<char[]> apiKeyProvider;

   public ApiKeyConfig(Supplier<char[]> apiKeyProvider) {
      this.apiKeyProvider = apiKeyProvider;
   }

   public Supplier<char[]> getApiKeyProvider() {
      return apiKeyProvider;
   }

   public void setApiKeyProvider(Supplier<char[]> apiKeyProvider) {
      this.apiKeyProvider = apiKeyProvider;
   }
}
