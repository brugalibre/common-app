package com.brugalibre.notification.send.messagebird.model;

import com.brugalibre.notification.send.common.model.CommonAlertSendResponse;
import com.messagebird.objects.MessageResponse;

public class MessageBirdSendAlertSendResponse extends CommonAlertSendResponse {

   public MessageBirdSendAlertSendResponse(int status, Object responseEntity) {
      super(status, responseEntity);
   }

   public static MessageBirdSendAlertSendResponse of(MessageResponse response) {
      return new MessageBirdSendAlertSendResponse(200, response.getBody());
   }
}
