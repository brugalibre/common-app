package com.brugalibre.notification.send.common.model;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;

import java.util.List;

public class MultipleAlertSendResponses implements AlertSendResponse {
   private final List<AlertSendResponse> alertSendResponses;

   public MultipleAlertSendResponses(List<AlertSendResponse> alertSendResponses) {
      this.alertSendResponses = alertSendResponses;
   }

   @Override
   public int getStatus() {
      return getStatusFromResponses(this.alertSendResponses);
   }

   @Override
   public Object getResponseEntity() {
      return getResponseBodyFromResponses(this.alertSendResponses);
   }

   public List<AlertSendResponse> getAlertSendResponses() {
      return alertSendResponses;
   }

   private static Object getResponseBodyFromResponses(List<AlertSendResponse> alertSendRespons) {
      return alertSendRespons.stream()
              .map(AlertSendResponse::getResponseEntity)
              .toList();
   }

   private static int getStatusFromResponses(List<AlertSendResponse> alertSendRespons) {
      List<Integer> allStatus = alertSendRespons.stream()
              .map(AlertSendResponse::getStatus)
              .distinct()
              .sorted()
              .toList();
      if (allStatus.size() == 1) {
         return allStatus.get(0);
      }
      // we just returned the one with the highest number
      // assuming that a server error is more important than a client-error
      // which is more important than a success
      return allStatus.get(allStatus.size() - 1);
   }

}
