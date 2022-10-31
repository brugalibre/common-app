package com.brugalibre.common.domain.exception;

import com.brugalibre.common.domain.text.TextFormatter;

public abstract class CommonDomainException extends RuntimeException {
   public CommonDomainException(String msg, Object... args) {
      super(getExceptionMessage(msg, args));
   }

   /**
    * Creates the message for this {@link CommonDomainException}
    *
    * @param msg  the message template
    * @param args the argument which replaces the placeholder
    * @return the final exception-message
    */
   public static String getExceptionMessage(String msg, Object[] args) {
      return TextFormatter.formatText(msg, args);
   }
}
