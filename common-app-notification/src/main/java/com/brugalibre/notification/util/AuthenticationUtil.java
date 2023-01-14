package com.brugalibre.notification.util;

import com.brugalibre.notification.config.AlertSendConfig;

import java.util.Base64;

public class AuthenticationUtil {
   private AuthenticationUtil() {
      // private
   }

   public static String getEncodedUsernameAndPassword(AlertSendConfig alertSendConfig) {
      String username = alertSendConfig.getUsername();
      String apiKey = alertSendConfig.getApiKey();
      return Base64.getEncoder().encodeToString((username + ":" + apiKey).getBytes());
   }
}
