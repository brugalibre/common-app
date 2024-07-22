package com.brugalibre.util.date;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilTest {

   @Test
   void getLocalDateFromString() {

      // Given
      String dateAsString1 = "01.01.2024";
      String dateAsString2 = "2024.01.05";

      // When
      LocalDate parsedDate = DateUtil.getLocalDateFromString(dateAsString1, "dd.MM.yyyy");
      LocalDate parsedDate2 = DateUtil.getLocalDateFromString(dateAsString2, "yyyy.MM.dd");

      // Then
      assertThat(parsedDate).isEqualTo(LocalDate.of(2024, 1, 1));
      assertThat(parsedDate2).isEqualTo(LocalDate.of(2024, 1, 5));
   }
}