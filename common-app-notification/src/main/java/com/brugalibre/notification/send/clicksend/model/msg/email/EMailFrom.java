package com.brugalibre.notification.send.clicksend.model.msg.email;

import jakarta.annotation.Nonnull;

public class EMailFrom {
   @Nonnull
   private int email_address_id;
   private String name;

   public static EMailFrom of(int originatorId, String name) {
      EMailFrom eMailFrom = new EMailFrom();
      eMailFrom.setEmail_address_id(originatorId);
      eMailFrom.setName(name);
      return eMailFrom;
   }

   public int getEmail_address_id() {
      return email_address_id;
   }

   public void setEmail_address_id(int email_address_id) {
      this.email_address_id = email_address_id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}

