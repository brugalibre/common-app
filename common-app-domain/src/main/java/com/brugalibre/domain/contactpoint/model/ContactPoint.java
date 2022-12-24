package com.brugalibre.domain.contactpoint.model;

import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.domain.contactpoint.ContactPointType;

public class ContactPoint implements DomainModel {

   private final String id;
   private final String userId;
   private final ContactPointType contactPointType;

   public ContactPoint(String id, String userId, ContactPointType contactPointType) {
      this.id = id;
      this.userId = userId;
      this.contactPointType = contactPointType;
   }

   @Override
   public String getId() {
      return id;
   }

   public String getUserId() {
      return userId;
   }

   public ContactPointType getContactPointType() {
      return contactPointType;
   }
}
