package com.brugalibre.domain.contactpoint.mobilephone.repository;

import com.brugalibre.common.domain.app.config.TestCommonAppPersistenceConfig;
import com.brugalibre.domain.contactpoint.mobilephone.exception.PhoneNrNotValidException;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(classes = TestCommonAppPersistenceConfig.class)
class MobilePhoneRepositoryTest {

   @Autowired
   private MobilePhoneRepository mobilePhoneRepository;

   @AfterEach
   public void cleanUp() {
      mobilePhoneRepository.deleteAll();
   }

   @Test
   void testCreateMobilePhoneEntityWithPhoneNumber() {
      // Given
      String phoneNr = "+41 (78) 123 45 23";
      String expectedPhoneNur = "0041781234523";
      MobilePhone mobilePhone = MobilePhone.of(phoneNr);

      // When
      MobilePhone savedMobilePhone = mobilePhoneRepository.save(mobilePhone);

      // Then
      assertThat(savedMobilePhone.getPhoneNr(), is(expectedPhoneNur));
   }

   @Test
   void testCreateMobilePhoneEntityAndUpdateWithInvalidPhoneNumber() {
      // Given
      String userId = "1234";
      String phoneNr = "+41 (78) 123 45 63";
      String newInvalidPhoneNr = "+41 (78) 123 45";
      MobilePhone mobilePhone = MobilePhone.of(userId, phoneNr);

      // When
      mobilePhoneRepository.save(mobilePhone);
      Executable exe = () -> mobilePhoneRepository.updatePhoneNr(mobilePhone.getUserId(), newInvalidPhoneNr);

      // Then
      Assertions.assertThrows(PhoneNrNotValidException.class, exe);
   }
}