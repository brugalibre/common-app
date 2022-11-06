package com.brugalibre.common.security.user.repository;


import com.brugalibre.common.security.user.model.User;
import com.brugalibre.persistence.user.UserEntity;
import com.brugalibre.persistence.user.dao.UserDao;
import com.brugalibre.persistence.user.validate.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   private final UserDao userDao;// we should use the UserRepository but then the domain-user should contain the Role
   private final PhoneNumberValidator phoneNumberValidator;

   @Autowired
   public UserDetailsServiceImpl(UserDao userDao) {
      this.userDao = userDao;
      this.phoneNumberValidator = new PhoneNumberValidator();
   }

   /**
    * Returns a {@link UserDetails} for the given username
    *
    * @param username the username
    * @return a {@link UserDetails} for the given username
    * @throws UsernameNotFoundException if there is no user with the given name
    */
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return getOptionalUserDetails(username)
              .orElseThrow(() -> new IllegalStateException("No User found for username '" + username + "'"));
   }

   public boolean existsUserByUsername(String username) {
      return getOptionalUserDetails(username)
              .isPresent();
   }

   /**
    * Saves the given {@link User} in the database
    *
    * @param user the {@link User}
    * @return a persisted instance of the given {@link User}
    */
   public User save(User user) {
      String normalizedPhoneNr = phoneNumberValidator.normalizePhoneNumber(user.getPhoneNr());
      UserEntity userEntity = this.userDao.save(new UserEntity(user.getUsername(), user.getPassword(), normalizedPhoneNr, user.getRoles()));
      return new User(userEntity);
   }

   private Optional<UserDetails> getOptionalUserDetails(String username) {
      return userDao.findByUsername(username)
              .map(User::new);
   }

   /**
    * Deletes the user with the given id
    *
    * @param userid the user-id to delete
    */
   public void delete(String userid) {
      userDao.deleteById(userid);
   }
}
