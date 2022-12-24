package com.brugalibre.persistence.contactpoint;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.domain.contactpoint.ContactPointType;
import com.brugalibre.persistence.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dm_contact_point_type", discriminatorType = DiscriminatorType.STRING)
public abstract class ContactPointEntity extends DomainEntity {

   @Enumerated(value = EnumType.STRING)
   private ContactPointType contactPointType;

   @ManyToOne
   @JoinColumn(name = "user_id")
   private UserEntity user;

   public ContactPointEntity() {
      super(null);
   }

   public ContactPointEntity(String id) {
      super(id);
   }

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