package com.brugalibre.common.security.rest.service;

import com.brugalibre.common.security.auth.register.UserRegisteredEvent;
import com.brugalibre.common.security.auth.register.UserRegisteredNotifier;
import com.brugalibre.common.security.auth.register.UserRegisteredObserver;
import com.brugalibre.common.security.i18n.TextResources;
import com.brugalibre.common.security.rest.api.UserAlreadyExistsException;
import com.brugalibre.common.security.rest.model.RegisterRequest;
import com.brugalibre.common.security.rest.model.RegisterResponse;
import com.brugalibre.common.security.user.model.User;
import com.brugalibre.common.security.user.repository.SanitizedRegisterUserRequest;
import com.brugalibre.common.security.user.repository.UserDetailsServiceImpl;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.persistence.user.UserEntity;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Service
public class UserRegisterService implements UserRegisteredNotifier {

   private static final Logger LOG = LoggerFactory.getLogger(UserRegisterService.class);
   private final UserDetailsServiceImpl userDetailsServiceImpl;
   private final UserRepository userRepository;
   private final PasswordEncoder encoder;
   private final List<UserRegisteredObserver> userRegisteredObservers;

   @Autowired
   public UserRegisterService(UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder encoder, UserRepository userRepository) {
      this.encoder = encoder;
      this.userRepository = userRepository;
      this.userDetailsServiceImpl = userDetailsServiceImpl;
      this.userRegisteredObservers = new ArrayList<>();
   }

   /**
    * Registers a new {@link org.springframework.security.core.userdetails.User} for the given {@link RegisterRequest}
    * Also all {@link UserRegisteredObserver} are notified if the user was registered successfully
    *
    * @param registerRequest the {@link RegisterRequest} with details about the {@link org.springframework.security.core.userdetails.User} to register
    * @return a {@link RegisterResponse} with a message describing the result
    * @throws UserAlreadyExistsException if there exists already a {@link UserEntity} for the same username as in the given {@link RegisterRequest}
    */
   @Transactional
   public RegisterResponse registerUser(RegisterRequest registerRequest) {
      if (userDetailsServiceImpl.existsUserByUsername(registerRequest.username())) {
         LOG.error("Registration failed! Username {} already taken", registerRequest.username());
         throw new UserAlreadyExistsException(TextResources.USERNAME_ALREADY_TAKEN);
      }

      // Create new user's account
      SanitizedRegisterUserRequest sanitizedRegisterUserRequest = new SanitizedRegisterUserRequest(registerRequest.username(), encoder.encode(registerRequest.password()), registerRequest.userPhoneNr(), registerRequest.roles());
      User persistedUser = userDetailsServiceImpl.save(sanitizedRegisterUserRequest);
      notifyUserRegisteredObservers(persistedUser, registerRequest);
      LOG.info("User {} registered successfully", registerRequest.username());
      return new RegisterResponse(TextResources.REGISTRATION_SUCCESSFUL);
   }

   private void notifyUserRegisteredObservers(User user, RegisterRequest registerRequest) {
      char[] passwordCopy = Arrays.copyOf(registerRequest.password().toCharArray(), registerRequest.password().length());
      UserRegisteredEvent userRegisteredEvent = getUserRegisteredEvent(user, passwordCopy);
      this.userRegisteredObservers.forEach(userRegisteredObserver -> userRegisteredObserver.userRegistered(userRegisteredEvent));
      Arrays.fill(passwordCopy, '0');
   }

   private UserRegisteredEvent getUserRegisteredEvent(User user, char[] passwordCopy) {
      com.brugalibre.domain.user.model.User domainUser = userRepository.getById(user.getId());
      return UserRegisteredEvent.of(domainUser, passwordCopy);
   }

   @Override
   public void addUserRegisteredObserver(UserRegisteredObserver userRegisteredObserver) {
      this.userRegisteredObservers.add(requireNonNull(userRegisteredObserver));
   }

   @Override
   public void removeUserRegisteredObserver(UserRegisteredObserver userRegisteredObserver) {
      this.userRegisteredObservers.remove(userRegisteredObserver);
   }


}
