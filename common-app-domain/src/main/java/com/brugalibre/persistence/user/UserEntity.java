package com.brugalibre.persistence.user;

import com.brugalibre.common.domain.persistence.DomainEntity;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

import static com.brugalibre.persistence.user.validate.PhoneNumberValidator.PHONE_NR_PATTERN;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity extends DomainEntity {

   @NotBlank
   private String username;

   @NotBlank
   private String password;

   @Pattern(regexp = PHONE_NR_PATTERN, message = "Phone-Nr format is not recognized")
   private String phoneNr;

   @ElementCollection
   @Enumerated(value = EnumType.STRING)
   @LazyCollection(LazyCollectionOption.FALSE)
   private List<Role> roles;

   public UserEntity() {
      super(null);
      this.roles = new ArrayList<>();
   }

   public UserEntity(String username, String password, String phoneNr, List<Role> roles) {
      super(null);
      this.username = username;
      this.password = password;
      this.phoneNr= phoneNr;
      this.roles = new ArrayList<>(roles);
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Role> getRoles() {
      return roles;
   }

   public void setRoles(List<Role> roles) {
      this.roles = new ArrayList<>(roles);
   }

   public String getPhoneNr() {
      return phoneNr;
   }

   public void setPhoneNr(String phoneNr) {
      this.phoneNr = phoneNr;
   }
}