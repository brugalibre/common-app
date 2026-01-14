package com.brugalibre.notification.api.v1.factory;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.send.clicksend.service.ClickSendAlertSendServiceImpl;
import com.brugalibre.notification.send.email.service.EMailSendServiceImpl;
import com.brugalibre.notification.send.messagebird.service.MessageBirdAlertSendServiceImpl;

/**
 * Common api for sending alerts
 */
public class AlertSendServiceFactory {

   public static AlertSendService getAlertSendService4Name(AlertType alertType) {
      switch (alertType) {
         case EMAIL -> {
            return new EMailSendServiceImpl();
         }
         case CLICK_SEND_SMS -> {
            return new ClickSendAlertSendServiceImpl();
         }
         case MESSAGE_BIRD_SMS -> {
            return new MessageBirdAlertSendServiceImpl();
         }
         default -> throw new IllegalArgumentException("No alert send service found for alert type: " + alertType);
      }
   }
}
