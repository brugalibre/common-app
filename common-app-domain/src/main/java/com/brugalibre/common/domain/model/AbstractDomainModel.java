package com.brugalibre.common.domain.model;

import static java.util.Objects.nonNull;

public abstract class AbstractDomainModel implements DomainModel {
   protected String id;

   @Override
   public String getId() {
      return id;
   }

   public void setId(String id) {
      if (nonNull(id)) {
         this.id = id;
      }
   }
}
