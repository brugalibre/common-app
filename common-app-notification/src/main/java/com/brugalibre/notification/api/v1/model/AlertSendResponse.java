package com.brugalibre.notification.api.v1.model;

import com.brugalibre.notification.api.v1.service.AlertSendService;

/**
 * The {@link AlertSendResponse} is the basic response to any send request from a {@link AlertSendService}
 */
public interface AlertSendResponse {

   static AlertSendResponse error(String errorMsg) {
      return new AlertSendResponse() {
         @Override
         public int getStatus() {
            return 500;
         }

         @Override
         public Object getResponseEntity() {
            return errorMsg;
         }
      };
   }

   /**
    * @return The http-status of the response
    */
   int getStatus();

   /**
    * @return The response body
    */
   Object getResponseEntity();
}
