package com.brugalibre.common.domain.text;

import org.slf4j.helpers.MessageFormatter;

public class TextFormatter {
   private TextFormatter() {
   }

   /**
    * Formates the given text with the given arguments
    *
    * @param msgTemplate the message template
    * @param args        the argument which replaces the placeholder
    * @return the formatted text
    */
   public static String formatText(String msgTemplate, Object[] args) {
      return MessageFormatter.format(msgTemplate, args).getMessage();
   }
}
