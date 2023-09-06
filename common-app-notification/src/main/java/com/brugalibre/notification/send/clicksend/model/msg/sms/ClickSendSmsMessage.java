package com.brugalibre.notification.send.clicksend.model.msg.sms;

import com.brugalibre.notification.send.clicksend.model.msg.common.ClickSendMessage;

import java.util.ArrayList;
import java.util.List;

public class ClickSendSmsMessage implements ClickSendMessage {
   private List<SingleClickSendSmsMsg> messages;

   public ClickSendSmsMessage(){
      this.messages = new ArrayList<>();
   }

   public void setMessages(List<SingleClickSendSmsMsg> messages) {
      this.messages = messages;
   }

   public List<SingleClickSendSmsMsg> getMessages() {
      return messages;
   }

   public ClickSendSmsMessage addMessages(SingleClickSendSmsMsg singleClickSendSmsMsg) {
      this.messages.add(singleClickSendSmsMsg);
      return this;
   }
}
