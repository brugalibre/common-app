package com.brugalibre.common.domain.repository;

import com.brugalibre.common.domain.exception.CommonDomainException;

public class NoDomainModelFoundException extends CommonDomainException {
   public NoDomainModelFoundException(String msg, Object... args) {
      super(msg, args);
   }
}
