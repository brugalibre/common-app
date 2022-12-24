package com.brugalibre.domain.contactpoint.mobilephone.repository;


import com.brugalibre.common.domain.repository.CommonDomainRepositoryImpl;
import com.brugalibre.domain.contactpoint.mobilephone.exception.PhoneNrNotValidException;
import com.brugalibre.domain.contactpoint.mobilephone.mapper.MobilePhoneEntityMapperImpl;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.persistence.contactpoint.mobilephone.MobilePhoneEntity;
import com.brugalibre.persistence.contactpoint.mobilephone.dao.MobilePhoneDao;
import com.brugalibre.persistence.contactpoint.mobilephone.validate.PhoneNumberValidator;
import org.springframework.stereotype.Service;

@Service
public class MobilePhoneRepository extends CommonDomainRepositoryImpl<MobilePhone, MobilePhoneEntity, MobilePhoneDao> {
   private final PhoneNumberValidator phoneNumberValidator;

   public MobilePhoneRepository(MobilePhoneDao domainDao) {
      super(domainDao, new MobilePhoneEntityMapperImpl());
      this.phoneNumberValidator = new PhoneNumberValidator();
   }

   /**
    * Updates the given phone-nr on the user with the given phoneId
    *
    * @param userId         the userId of the {@link MobilePhone} to update
    * @param newPhoneNumber the new phone-nr to set
    */
   public void updatePhoneNr(String userId, String newPhoneNumber) {
      String normalizedNewPhoneNumber = phoneNumberValidator.normalizePhoneNumber(newPhoneNumber);
      validate(normalizedNewPhoneNumber);
      domainDao.updatePhoneNr(normalizedNewPhoneNumber, userId);
   }

   private void validate(String normalizedNewPhoneNumber) {
      if (phoneNumberValidator.isNotValid(normalizedNewPhoneNumber)) {
         throw new PhoneNrNotValidException("Phone-Nr {} is not valid!", normalizedNewPhoneNumber);
      }
   }
}
