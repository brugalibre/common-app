package com.brugalibre.common.security.user.repository;


import com.brugalibre.common.security.rest.model.RegisterRequest;
import com.brugalibre.common.security.user.model.User;
import com.brugalibre.persistence.contactpoint.ContactPointEntity;
import com.brugalibre.persistence.contactpoint.mobilephone.MobilePhoneEntity;
import com.brugalibre.persistence.user.UserEntity;
import com.brugalibre.persistence.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   private final UserDao userDao;// we should use the UserRepository but then the domain-user should contain the Role

   @Autowired
   public UserDetailsServiceImpl(UserDao userDao) {
      this.userDao = userDao;
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
    * Creates and persists a new {@link User} for the given {@link SanitizedRegisterUserRequest}
    *
    * @param registerRequest the {@link SanitizedRegisterUserRequest} which contains all the necessary information
    * @return a persisted instance of the given {@link User}
    */
   public User save(SanitizedRegisterUserRequest registerRequest) {
      UserEntity userEntity = this.userDao.save(createUserEntity(registerRequest));
      return new User(userEntity);
   }

   private UserEntity createUserEntity(SanitizedRegisterUserRequest registerRequest) {
      List<ContactPointEntity> contactPoints = List.of(new MobilePhoneEntity(null, registerRequest.userPhoneNr()));
      return new UserEntity(registerRequest.username(), registerRequest.password(), User.toRoles(registerRequest.roles()), contactPoints);
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
