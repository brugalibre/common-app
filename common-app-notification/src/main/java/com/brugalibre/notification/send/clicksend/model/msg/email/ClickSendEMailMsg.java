package com.brugalibre.notification.send.clicksend.model.msg.email;

import com.brugalibre.notification.send.clicksend.model.msg.common.ClickSendMessage;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class ClickSendEMailMsg implements ClickSendMessage {

   private String body;

   @Nonnull
   private EMailFrom from;
   @Nonnull
   private List<EMailTo> to;

   private String subject;

   public ClickSendEMailMsg() {
      this.to = new ArrayList<>();
   }

   public EMailFrom getFrom() {
      return from;
   }

   public void setFrom(EMailFrom from) {
      this.from = from;
   }

   public ClickSendEMailMsg addTo(EMailTo to) {
      this.to.add(to);
      return this;
   }

   public List<EMailTo> getTo() {
      return to;
   }

   public void setTo(List<EMailTo> to) {
      this.to = to;
   }

   public String getSubject() {
      return subject;
   }

   public void setSubject(String subject) {
      this.subject = subject;
   }

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }
}

