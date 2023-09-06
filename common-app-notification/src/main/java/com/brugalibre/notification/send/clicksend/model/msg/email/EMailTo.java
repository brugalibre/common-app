package com.brugalibre.notification.send.clicksend.model.msg.email;

import jakarta.annotation.Nonnull;

public class EMailTo {
   @Nonnull
   private String email;
   private String name;

   public static EMailTo of(String email) {
      EMailTo eMailTo = new EMailTo();
      eMailTo.setEmail(email);
      return eMailTo;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}

