package com.brugalibre.notification.sms.model.response;

import com.brugalibre.notification.api.AlertResponse;

public abstract class CommonAlertResponse implements AlertResponse {

   private final int status;
   private final Object responseEntity;

   public CommonAlertResponse(int status, Object responseEntity) {
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
