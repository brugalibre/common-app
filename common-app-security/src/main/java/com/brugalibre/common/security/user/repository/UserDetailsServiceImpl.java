package com.brugalibre.common.security.user.repository;


import com.brugalibre.common.security.user.model.User;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.domain.contactpoint.model.ContactPoint;
import com.brugalibre.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   private final UserRepository userRepository;

   @Autowired
   public UserDetailsServiceImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
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
      com.brugalibre.domain.user.model.User user = this.userRepository.save(createUser(registerRequest));
      return new User(user);
   }

   private com.brugalibre.domain.user.model.User createUser(SanitizedRegisterUserRequest registerRequest) {
      List<ContactPoint> contactPoints = List.of(MobilePhone.of(registerRequest.userPhoneNr()));
      return new com.brugalibre.domain.user.model.User(null, registerRequest.username(), registerRequest.password(), contactPoints, User.toRoles(registerRequest.roles()));
   }

   private Optional<UserDetails> getOptionalUserDetails(String username) {
      return userRepository.findByUsername(username)
              .map(User::new);
   }

   /**
    * Deletes the user with the given id
    *
    * @param userid the user-id to delete
    */
   public void delete(String userid) {
      userRepository.deleteById(userid);
   }
}
