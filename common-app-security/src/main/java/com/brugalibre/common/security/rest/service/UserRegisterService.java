package com.brugalibre.common.security.rest.service;

import com.brugalibre.common.domain.text.TextFormatter;
import com.brugalibre.common.security.auth.register.UserRegisteredEvent;
import com.brugalibre.common.security.auth.register.UserRegisteredNotifier;
import com.brugalibre.common.security.auth.register.UserRegisteredObserver;
import com.brugalibre.common.security.i18n.TextResources;
import com.brugalibre.common.security.rest.api.UserAlreadyExistsException;
import com.brugalibre.common.security.rest.model.RegisterRequest;
import com.brugalibre.common.security.rest.model.RegisterResponse;
import com.brugalibre.common.security.user.model.User;
import com.brugalibre.common.security.user.repository.UserDetailsServiceImpl;
import com.brugalibre.persistence.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Service
public class UserRegisterService implements UserRegisteredNotifier {

   private static final Logger LOG = LoggerFactory.getLogger(UserRegisterService.class);
   private final UserDetailsServiceImpl userDetailsServiceImpl;
   private final PasswordEncoder encoder;
   private final List<UserRegisteredObserver> userRegisteredObservers;

   @Autowired
   public UserRegisterService(PasswordEncoder encoder, UserDetailsServiceImpl userDetailsServiceImpl) {
      this.encoder = encoder;
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
   public RegisterResponse registerUser(RegisterRequest registerRequest) {
      if (userDetailsServiceImpl.existsUserByUsername(registerRequest.username())) {
         LOG.error("Registration failed! Username {} already taken", registerRequest.username());
         throw new UserAlreadyExistsException(TextResources.USERNAME_ALREADY_TAKEN);
      }

      // Create new user's account
      User user = createNewUser(registerRequest);
      User persistedUser = userDetailsServiceImpl.save(user);
      notifyUserRegisteredObservers(persistedUser, registerRequest);
      LOG.info("User {} registered successfully", registerRequest.username());
      return new RegisterResponse("Registration successful!");
   }

   private User createNewUser(RegisterRequest registerRequest) {
      return new User(registerRequest.username(), encoder.encode(registerRequest.password()), registerRequest.userPhoneNr(), User.toRoles(registerRequest.roles()));
   }

   private void notifyUserRegisteredObservers(User user, RegisterRequest registerRequest) {
      char[] passwordCopy = Arrays.copyOf(registerRequest.password().toCharArray(), registerRequest.password().length());
      UserRegisteredEvent userRegisteredEvent = UserRegisteredEvent.of(user, passwordCopy);
      this.userRegisteredObservers.forEach(notifyUserRegistered(user, userRegisteredEvent));
      Arrays.fill(passwordCopy, '0');
   }

   private Consumer<UserRegisteredObserver> notifyUserRegistered(User user, UserRegisteredEvent userRegisteredEvent) {
      return userRegisteredObserver -> {
         try {
            userRegisteredObserver.userRegistered(userRegisteredEvent);
         } catch (Exception e) {
            LOG.error(TextFormatter.formatText("Exception while notifying observer {}. Rolling back user creation", new Object[]{userRegisteredObserver}), e);
            this.userDetailsServiceImpl.delete(user.getId());
            throw e;
         }
      };
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
