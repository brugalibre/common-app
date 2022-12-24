package com.brugalibre.domain.contactpoint.mobilephone.exception;

import com.brugalibre.common.domain.exception.CommonDomainException;

public class PhoneNrNotValidException extends CommonDomainException {
   public PhoneNrNotValidException(String msg, Object... args) {
      super(msg, args);
   }
}
