package com.brugalibre.persistence.user.validate;

import static java.util.Objects.nonNull;

public class PhoneNumberValidator {

   /**
    * Regex pattern for validation a phone-nr. Right now it validates only swiss numbers like
    * -  0041795306238
    * -  0791234567
    * <b>Note</b> Any phone number has to be normalized by calling {@link #normalizePhoneNumber(String)}!
    */
   public static final String PHONE_NR_PATTERN = "(^$)|(^\\d{10}$)|(^\\d{4}\\d{9}$)";

   /**
    * Removes all illegal characters from the given phone-Number.
    * So phone-numbers like
    * +4178 123 45 67
    * (078) 123-45-67
    * <p>
    * becomes
    * 0781234567 resp. 0041781234567
    *
    * @param phoneNumber the phone-number
    * @return the normalized the phone-number
    */
   public String normalizePhoneNumber(String phoneNumber) {
      return phoneNumber.replace("+", "00")
              .replace("(", "")
              .replace(")", "")
              .replace(" ", "")
              .replace("-", "");
   }

   /**
    * Validates if the given phone-nr matches the PHONE_NR_PATTERN
    *
    * @param phoneNr the phone-nr to validate
    * @return <code>true</code>if the given phone-nr matches the PHONE_NR_PATTERN or <code>false</code> if not
    */
   public boolean isNotValid(String phoneNr) {
      return nonNull(phoneNr) && !phoneNr.matches(PHONE_NR_PATTERN);
   }
}
