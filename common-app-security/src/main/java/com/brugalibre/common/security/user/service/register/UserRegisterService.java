package com.brugalibre.common.security.user.service.register;

import com.brugalibre.common.domain.text.TextFormatter;
import com.brugalibre.common.security.auth.register.UserRegisteredEvent;
import com.brugalibre.common.security.auth.register.UserRegisteredNotifier;
import com.brugalibre.common.security.auth.register.UserRegisteredObserver;
import com.brugalibre.common.security.i18n.TextResources;
import com.brugalibre.common.security.rest.UserAlreadyExistsException;
import com.brugalibre.common.security.user.model.User;
import com.brugalibre.common.security.user.model.register.RegisterRequest;
import com.brugalibre.common.security.user.model.register.RegisterResponse;
import com.brugalibre.common.security.user.repository.SanitizedRegisterUserRequest;
import com.brugalibre.common.security.user.repository.UserDetailsServiceImpl;
import com.brugalibre.domain.user.repository.UserRepository;
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
    *<p>
    * <b>Note:</b> If any of the registered {@link UserRegisteredObserver}s fails and throws an {@link Exception},
    * the registration is rollbacked. This ensures one atomar transaction across all observers. Anyway, this method is not
    * annotated with @Transactional in order to avoid any transactional behaviour to any of the observers
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
      SanitizedRegisterUserRequest sanitizedRegisterUserRequest = new SanitizedRegisterUserRequest(registerRequest.username(), encoder.encode(registerRequest.password()), registerRequest.userPhoneNr(), registerRequest.roles());
      User persistedUser = userDetailsServiceImpl.save(sanitizedRegisterUserRequest);
      notifyUserRegisteredObservers(persistedUser, registerRequest);
      LOG.info("User {} registered successfully", registerRequest.username());
      return new RegisterResponse(TextResources.REGISTRATION_SUCCESSFUL);
   }

   private void notifyUserRegisteredObservers(User user, RegisterRequest registerRequest) {
      char[] passwordCopy = Arrays.copyOf(registerRequest.password().toCharArray(), registerRequest.password().length());
      UserRegisteredEvent userRegisteredEvent = getUserRegisteredEvent(user, passwordCopy);
      this.userRegisteredObservers.forEach(notifyUserRegistered(userRegisteredEvent));
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

   private Consumer<UserRegisteredObserver> notifyUserRegistered(UserRegisteredEvent userRegisteredEvent) {
      return userRegisteredObserver -> {
         try {
            userRegisteredObserver.userRegistered(userRegisteredEvent);
         } catch (Exception e) {
            LOG.error(TextFormatter.formatText("Exception while notifying observer {}. Rolling back user creation", new Object[]{userRegisteredObserver}), e);
            this.userDetailsServiceImpl.delete(userRegisteredEvent.userId());
            throw e;
         }
      };
   }
}
