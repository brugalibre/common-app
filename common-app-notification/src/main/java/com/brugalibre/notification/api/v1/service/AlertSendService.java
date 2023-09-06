package com.brugalibre.notification.api.v1.service;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.common.model.AlertSendInfos;

/**
 * Common api for sending alerts
 */
public interface AlertSendService {

   /**
    * Sends an alert with the given configuration (contains receivers, api key and so on) and the given message
    *
    * @param alertSendConfig the {@link AlertSendConfig}
    * @param alertSendInfos  the {@link AlertSendInfos} contains message to send as well as the receivers
    * @return a {@link AlertSendResponse} from the actual sms api service
    */
   AlertSendResponse sendAlert(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) throws AlertSendException;
}
