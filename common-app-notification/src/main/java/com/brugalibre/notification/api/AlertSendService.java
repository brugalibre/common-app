package com.brugalibre.notification.api;

import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.AlertSendInfos;

/**
 * Common api for sending alerts
 */
public interface AlertSendService {

   /**
    * Sends an alert with the given configuration (contains receivers, api key and so on) and the given message
    *
    * @param alertSendConfig the {@link AlertSendConfig}
    * @param alertSendInfos  the {@link AlertSendInfos} contains message to send as well as the receivers
    * @return a {@link AlertResponse} from the actual sms api service
    */
   AlertResponse sendAlert(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) throws AlertSendException;
}