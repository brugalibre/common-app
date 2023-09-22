package com.brugalibre.domain.user.repository;


import com.brugalibre.common.domain.repository.CommonDomainRepositoryImpl;
import com.brugalibre.domain.user.mapper.UserEntityMapperImpl;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.persistence.contactpoint.mobilephone.validate.PhoneNumberValidator;
import com.brugalibre.persistence.user.UserEntity;
import com.brugalibre.persistence.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepository extends CommonDomainRepositoryImpl<User, UserEntity, UserDao> {

   private final PhoneNumberValidator phoneNumberValidator;

   @Autowired
   public UserRepository(UserDao userDao) {
      super(userDao, new UserEntityMapperImpl());
      this.phoneNumberValidator = new PhoneNumberValidator();
   }

   /**
    * Finds an optional {@link User} by its username
    *
    * @param username the username
    * @return an {@link Optional} of an {@link User}
    */
   public Optional<User> findByUsername(String username) {
      return domainDao.findByUsername(username)
              .map(domainModelMapper::map2DomainModel);
   }

   /**
    * Finds an optional {@link User} by its phone-nr. First the given phone-nr is normalized by the {@link PhoneNumberValidator}
    * If there is no match we assume that the given phone-nr contains a country-code like +41/0041 where as the persisted
    * phone-nr does not have such a country code. So we'll replace that and try again
    *
    * @param phoneNr the phoneNr
    * @see PhoneNumberValidator
    * @return an {@link Optional} of an {@link User}
    */
   public Optional<User> findByPhoneNr(String phoneNr) {
      String normalizePhoneNumber = phoneNumberValidator.normalizePhoneNumber(phoneNr);
      Optional<UserEntity> foundUserByPhoneNr = domainDao.findByPhoneNr(normalizePhoneNumber);
      if (foundUserByPhoneNr.isEmpty()) {
         foundUserByPhoneNr = domainDao.findByPhoneNr(normalizePhoneNumber.replace("0041", "0"));
      }
      return foundUserByPhoneNr
              .map(domainModelMapper::map2DomainModel);
   }

    /**
     * Changes the {@link User}s password
     *
     * @param newPassword the new password value to set
     * @param userId      the id of the User whose password has to be changed
     */
    public void changePassword(String newPassword, String userId) {
        domainDao.setPassword(newPassword, userId);
    }

    /**
     * Changes the {@link User}s username
     *
     * @param newUsername the new username value to set
     * @param userId      the id of the User whose name has to be changed
     */
    public void changeUsername(String newUsername, String userId) {
        domainDao.setUsername(newUsername, userId);
    }
}
