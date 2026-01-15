package com.brugalibre.persistence.contactpoint;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.domain.contactpoint.ContactPointType;
import com.brugalibre.persistence.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ContactPointEntity extends DomainEntity {

   @Enumerated(value = EnumType.STRING)
   private ContactPointType contactPointType;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private UserEntity user;

   public ContactPointType getContactPointType() {
      return contactPointType;
   }

   public void setContactPointType(ContactPointType contactPointType) {
      this.contactPointType = contactPointType;
   }

   public UserEntity getUser() {
      return user;
   }

   public void setUser(UserEntity userEntity) {
      this.user = userEntity;
   }
}