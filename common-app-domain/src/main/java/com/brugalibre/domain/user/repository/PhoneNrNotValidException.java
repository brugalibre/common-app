package com.brugalibre.domain.user.repository;

import com.brugalibre.common.domain.exception.CommonDomainException;

public class PhoneNrNotValidException extends CommonDomainException {
   public PhoneNrNotValidException(String msg, Object... args) {
      super(msg, args);
   }
}
