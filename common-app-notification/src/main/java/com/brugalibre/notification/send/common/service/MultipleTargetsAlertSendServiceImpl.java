package com.brugalibre.notification.send.common.service;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.notification.send.common.model.MultipleAlertSendResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class MultipleTargetsAlertSendServiceImpl implements AlertSendService {
   private final Map<AlertType, AlertSendService> alertTypeAlertSendServiceMap;
   private final Function<AlertType, AlertSendService> alertSendServiceProvider;

   public MultipleTargetsAlertSendServiceImpl(Function<AlertType, AlertSendService> alertSendServiceProvider) {
      this.alertTypeAlertSendServiceMap = new HashMap<>();
      this.alertSendServiceProvider = requireNonNull(alertSendServiceProvider);
   }

   @Override
   public AlertSendResponse sendAlert(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) throws AlertSendException {
      List<AlertSendResponse> alertSendResponses = new ArrayList<>();
      for (AlertType alertType : alertSendConfig.getAlertTypes()) {
         AlertSendService alertSendService = getAlertSendService(alertType);
         AlertSendResponse alertSendResponse = alertSendService.sendAlert(alertSendConfig, alertSendInfos);
         alertSendResponses.add(alertSendResponse);
      }
      return new MultipleAlertSendResponses(alertSendResponses);
   }

   private AlertSendService getAlertSendService(AlertType alertType) {
      if (alertTypeAlertSendServiceMap.containsKey(alertType)) {
         return alertTypeAlertSendServiceMap.get(alertType);
      }
      AlertSendService alertSendService = alertSendServiceProvider.apply(alertType);
      alertTypeAlertSendServiceMap.put(alertType, alertSendService);
      return alertSendService;
   }
}
