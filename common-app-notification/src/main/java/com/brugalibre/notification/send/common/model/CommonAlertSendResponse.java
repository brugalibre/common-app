package com.brugalibre.notification.send.common.model;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;

public abstract class CommonAlertSendResponse implements AlertSendResponse {

   private final int status;
   private final Object responseEntity;

   public CommonAlertSendResponse(int status, Object responseEntity) {
      this.status = status;
      this.responseEntity = responseEntity;
   }

   @Override
   public int getStatus() {
      return status;
   }

   @Override
   public Object getResponseEntity() {
      return responseEntity;
   }
}
