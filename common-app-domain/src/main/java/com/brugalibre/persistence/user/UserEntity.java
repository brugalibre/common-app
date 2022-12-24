package com.brugalibre.persistence.user;

import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.persistence.contactpoint.ContactPointEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity extends DomainEntity {

   @NotBlank
   private String username;

   @NotBlank
   private String password;

   @ElementCollection
   @Enumerated(value = EnumType.STRING)
   @LazyCollection(LazyCollectionOption.FALSE)
   private List<Role> roles;

   /*
    * yes eager, since we access them in the mapper, where the session is already closed.
    * Besides, this shouldn't be a performance issue, since there are not that many contact-points.
    */
   @OneToMany(targetEntity = ContactPointEntity.class,
           mappedBy = "user",
           cascade = CascadeType.ALL,
           fetch = FetchType.EAGER,
           orphanRemoval = true)
   @NotNull
   private List<ContactPointEntity> contactPoints;

   public UserEntity() {
      super(null);
      this.roles = new ArrayList<>();
      this.contactPoints = new ArrayList<>();
   }

   public UserEntity(String username, String password, List<Role> roles, List<ContactPointEntity> contactPoints) {
      super(null);
      this.username = username;
      this.password = password;
      this.contactPoints = new ArrayList<>(contactPoints);
      contactPoints.forEach(contactPoint -> contactPoint.setUser(this));
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

   public List<ContactPointEntity> getContactPoints() {
      return contactPoints;
   }

   public void setContactPoints(List<ContactPointEntity> contactPointEntities) {
      this.contactPoints = contactPointEntities;
   }
}