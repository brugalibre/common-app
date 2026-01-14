package com.brugalibre.notification.util;

import java.util.Base64;
import java.util.function.Supplier;

public class AuthenticationUtil {
   private AuthenticationUtil() {
      // private
   }

   public static String getEncodedUsernameAndPassword(String username, Supplier<char[]> apiKeyProvider) {
      return Base64.getEncoder().encodeToString((username + ":" + String.valueOf(apiKeyProvider.get())).getBytes());
   }
}
