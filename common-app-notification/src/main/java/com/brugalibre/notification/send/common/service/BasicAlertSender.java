package com.brugalibre.notification.send.common.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.api.v1.factory.AlertSendServiceFactory;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.config.AlertSendConfigProvider;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * The {@link BasicAlertSender} sends an alert configured by a {@link AlertSendConfig} to one or more subscribers
 */
public class BasicAlertSender {

   private static final Logger LOG = LoggerFactory.getLogger(BasicAlertSender.class);
   protected final Function<AlertType, AlertSendService> alertServiceFunction;
   protected final AlertSendConfigProvider configProvider;

   /**
    * Constructor only for testing purpose
    */
   public BasicAlertSender(AlertSendConfigProvider configProvider, Function<AlertType, AlertSendService> alertServiceFunction) {
      this.configProvider = configProvider;
      this.alertServiceFunction = alertServiceFunction;
   }

   /**
    * Default constructor. Uses the ALERT_API_CONST_FILE and the {@link AlertSendServiceFactory} in order to create an {@link AlertSendService}
    */
   public BasicAlertSender(AlertSendConfigProvider configProvider) {
      this(configProvider, AlertSendServiceFactory::getAlertSendService4Name);
   }

   /**
    * Sends the given text message to the configured receivers, defined in the {@link AlertSendConfig}
    *
    * @param alertSendInfos the {@link AlertSendInfos} with message to send and receivers
    * @return an {@link AlertSendResponse}
    */
   public AlertSendResponse sendMessage(AlertSendInfos alertSendInfos) {
      AlertSendConfig alertSendConfig = configProvider.getAlertSendConfig();
      try {
         return getAlertSendApi(alertSendInfos).sendAlert(alertSendConfig, alertSendInfos);
      } catch (AlertSendException e) {
         LOG.error("Sending of alert '{}' failed!", alertSendInfos.msg(), e);
         return AlertSendResponse.error(e.getMessage());
      }
   }

   private AlertSendService getAlertSendApi(AlertSendInfos alertSendInfos) {
      return alertServiceFunction.apply(alertSendInfos.alertType());
   }
}
