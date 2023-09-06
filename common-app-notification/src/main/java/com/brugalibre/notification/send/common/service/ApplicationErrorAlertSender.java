package com.brugalibre.notification.send.common.service;

import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.config.AlertSendConfigProvider;
import com.brugalibre.notification.send.common.model.AlertSendInfos;

/**
 * The {@link ApplicationErrorAlertSender} sends an alert configured by a {@link AlertSendConfig} to one or more subscribers
 */
public class ApplicationErrorAlertSender extends BasicAlertSender {

   private ApplicationErrorAlertSender(AlertSendConfigProvider configProvider) {
      super(configProvider);
   }

   /**
    * Creates a new {@link ApplicationErrorAlertSender} with the given {@link AlertSendConfigProvider}
    *
    * @param alertSendConfigProvider the {@link AlertSendConfigProvider} to use
    * @return a new {@link ApplicationErrorAlertSender}
    */
   public static ApplicationErrorAlertSender of(AlertSendConfigProvider alertSendConfigProvider) {
      return new ApplicationErrorAlertSender(alertSendConfigProvider);
   }

   /**
    * Sends an error message regarding a failure in the application
    * This message is send to the administrator of the application
    *
    * @param errorTitle the error title
    * @param errorMsg   the error message
    */
   public void sendApplicationErrorMessage(String errorTitle, String errorMsg) {
      AlertSendInfos alertSendInfos = new AlertSendInfos(errorTitle, errorMsg, configProvider.getAlertSendConfig().getOnApplicationErrorReceivers());
      super.sendMessage(alertSendInfos);
   }
}
