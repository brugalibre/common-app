package com.brugalibre.notification.send.clicksend.model.response;

import com.brugalibre.notification.send.common.model.CommonAlertSendResponse;

import jakarta.ws.rs.core.Response;

public class ClickSendAlertSendResponse extends CommonAlertSendResponse {

   public ClickSendAlertSendResponse(int status, Object responseEntity) {
      super(status, responseEntity);
   }

   public static ClickSendAlertSendResponse of(Response response) {
      return new ClickSendAlertSendResponse(response.getStatus(), response.getStatusInfo());
   }
}
