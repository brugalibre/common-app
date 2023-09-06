package com.brugalibre.notification.send.clicksend.model.msg.sms;

public class SingleClickSendSmsMsg {

   private String from;
   private String to;
   private String body;

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public String getFrom() {
      return from;
   }

   public void setFrom(String from) {
      this.from = from;
   }

   public String getTo() {
      return to;
   }

   public void setTo(String to) {
      this.to = to;
   }
}

