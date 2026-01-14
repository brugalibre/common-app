package com.brugalibre.notification.send.common.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.config.AlertSendConfigProvider;
import com.brugalibre.notification.send.common.model.AlertSendInfos;

import java.util.function.Function;

/**
 * The {@link ApplicationErrorAlertSender} sends an alert configured by a {@link AlertSendConfig} to one or more subscribers
 */
public class ApplicationErrorAlertSender extends BasicAlertSender {

   /**
    * Visible for testing only
    *
    * @param configProvider       the {@link AlertSendConfigProvider}
    * @param alertServiceFunction the function to get the {@link AlertSendService}
    */
   ApplicationErrorAlertSender(AlertSendConfigProvider configProvider, Function<AlertType, AlertSendService> alertServiceFunction) {
      super(configProvider, alertServiceFunction);
   }

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
      AlertSendInfos alertSendInfos = new AlertSendInfos(configProvider.getAlertSendConfig().getOnApplicationErrorAlertType(), errorTitle, errorMsg,
              configProvider.getAlertSendConfig().getOnApplicationErrorReceivers());
      super.sendMessage(alertSendInfos);
   }
}
