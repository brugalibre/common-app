package com.brugalibre.notification.send.clicksend.config;

/**
 * Contains static configuration values for click send
 */
public final class ClickSendConfig {

   private static final String CLICKSEND_V3_BASE_REST_URL = "https://rest.clicksend.com/v3/";
   public static final String CLICKSEND_V3_SMS_SEND_URL = CLICKSEND_V3_BASE_REST_URL + "sms/send";
   public static final String CLICKSEND_V3_EMAIL_SEND_URL = CLICKSEND_V3_BASE_REST_URL + "email/send";
   private ClickSendConfig() {
      // private
   }
}
