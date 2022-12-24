package com.brugalibre.domain.contactpoint.mobilephone.model;

import com.brugalibre.domain.contactpoint.ContactPointType;
import com.brugalibre.domain.contactpoint.model.ContactPoint;

public class MobilePhone extends ContactPoint {
   private final String phoneNr;

   public MobilePhone(String id, String userId, String phoneNr, ContactPointType contactPointType) {
      super(id, userId, contactPointType);
      this.phoneNr = phoneNr;
   }

   public static MobilePhone of(String phoneNr) {
      return new MobilePhone(null, null, phoneNr, ContactPointType.MOBILE_PHONE);
   }

   public static MobilePhone of(String userId, String phoneNr) {
      return new MobilePhone(null, userId, phoneNr, ContactPointType.MOBILE_PHONE);
   }

   public String getPhoneNr() {
      return phoneNr;
   }
}
