package com.brugalibre.notification.api.factory;

import com.brugalibre.notification.api.AlertSendService;
import com.brugalibre.notification.sms.clicksend.ClickSendAlertSendServiceImpl;
import com.brugalibre.notification.sms.messagebird.MessageBirdAlertSendServiceImpl;

import static com.brugalibre.notification.constants.AlertConstants.CLICK_SEND_SMS_SERVICE_NAME;
import static com.brugalibre.notification.constants.AlertConstants.MESSAGE_BIRD_SMS_SERVICE_NAME;

/**
 * Common api for sending alerts
 */
public class AlertSendServiceFactory {

   public static AlertSendService getAlertSendService4Name(String alertServiceName) {
      if (CLICK_SEND_SMS_SERVICE_NAME.equals(alertServiceName)) {
         return new ClickSendAlertSendServiceImpl();
      } else if (MESSAGE_BIRD_SMS_SERVICE_NAME.equals(alertServiceName)) {
         return new MessageBirdAlertSendServiceImpl();
      }
      throw new IllegalStateException("Unknown alert-send-service '" + alertServiceName + "'");
   }
}
