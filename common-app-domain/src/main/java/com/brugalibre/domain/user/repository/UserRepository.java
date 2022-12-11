package com.brugalibre.domain.user.repository;


import com.brugalibre.common.domain.repository.CommonDomainRepositoryImpl;
import com.brugalibre.domain.user.mapper.UserEntityMapperImpl;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.persistence.user.UserEntity;
import com.brugalibre.persistence.user.dao.UserDao;
import com.brugalibre.persistence.user.validate.PhoneNumberValidator;
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

   @Override
   public User save(User user) {
      User userToSave = normalizePhoneNumberIfNecessary(user);
      return super.save(userToSave);
   }

   /**
    * Updates the given phone-nr on the user with the given username
    *
    * @param username       the name of the user to update
    * @param newPhoneNumber the new phone-nr to set
    */
   public void updatePhoneNr(String username, String newPhoneNumber) {
      String validNewPhoneNumber = getNormalizedPhoneNr(newPhoneNumber);
      validatePhoneNr(newPhoneNumber);
      domainDao.updatePhoneNr(validNewPhoneNumber, username);
   }

   private void validatePhoneNr(String newPhoneNumber) {
      if (phoneNumberValidator.isNotValid(newPhoneNumber)) {
         // Still not valid -> probably contains invalid characters or phone-nr pattern does not match
         throw new PhoneNrNotValidException("Phone-Nr '{}' is not valid!", newPhoneNumber);
      }
   }

   private String getNormalizedPhoneNr(String newPhoneNumber) {
      String validNewPhoneNumber = newPhoneNumber;
      if (phoneNumberValidator.isNotValid(newPhoneNumber)) {
         validNewPhoneNumber = phoneNumberValidator.normalizePhoneNumber(newPhoneNumber);
      }
      return validNewPhoneNumber;
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

   private User normalizePhoneNumberIfNecessary(User user) {
      User userToSave = user;
      if (phoneNumberValidator.isNotValid(user.phoneNr())) {
         String validPhoneNr = phoneNumberValidator.normalizePhoneNumber(user.phoneNr());
         userToSave = new User(user.id(), user.username(), user.password(), validPhoneNr);
      }
      return userToSave;
   }
}
