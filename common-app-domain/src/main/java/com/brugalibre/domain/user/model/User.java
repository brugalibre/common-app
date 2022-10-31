package com.brugalibre.domain.user.model;

import com.brugalibre.common.domain.model.DomainModel;

public record User(String id, String username, String password, String phoneNr) implements DomainModel {
   @Override
   public String getId() {
      return id;
   }
}
