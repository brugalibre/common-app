package com.brugalibre.persistence.contactpoint.mobilephone;

import com.brugalibre.domain.contactpoint.ContactPointType;
import com.brugalibre.persistence.contactpoint.ContactPointEntity;
import com.brugalibre.persistence.contactpoint.mobilephone.validate.PhoneNumberValidator;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import static com.brugalibre.persistence.contactpoint.mobilephone.validate.PhoneNumberValidator.PHONE_NR_PATTERN;

@Entity
@DiscriminatorValue("MOBILE_PHONE")
@Table(name = "mobilephone", uniqueConstraints = {@UniqueConstraint(columnNames = "phoneNr")})
public class MobilePhoneEntity extends ContactPointEntity {

   @Pattern(regexp = PHONE_NR_PATTERN, message = "Phone-Nr format is not recognized")
   @NotBlank
   private String phoneNr;

   public MobilePhoneEntity(String id, String phoneNr) {
      super(id);
      setPhoneNr(phoneNr);
      setContactPointType(ContactPointType.MOBILE_PHONE);
   }

   public MobilePhoneEntity() {
      super(null);
      setContactPointType(ContactPointType.MOBILE_PHONE);
   }

   public String getPhoneNr() {
      return phoneNr;
   }

   public void setPhoneNr(String phoneNr) {
      this.phoneNr = new PhoneNumberValidator().normalizePhoneNumber(phoneNr);
   }
}