package com.brugalibre.notification.send.common.model;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import jakarta.ws.rs.core.Response;

public class CommonAlertSendResponse implements AlertSendResponse {

   private final int status;
   private final Object responseEntity;

   public CommonAlertSendResponse(int status, Object responseEntity) {
      this.status = status;
      this.responseEntity = responseEntity;
   }

   public static CommonAlertSendResponse of(Response response) {
      return new CommonAlertSendResponse(response.getStatus(), response.getStatusInfo());
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
