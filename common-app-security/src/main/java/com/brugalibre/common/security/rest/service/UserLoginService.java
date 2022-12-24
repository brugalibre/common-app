package com.brugalibre.common.security.rest.service;

import com.brugalibre.common.domain.text.TextFormatter;
import com.brugalibre.common.security.auth.JwtUtils;
import com.brugalibre.common.security.auth.login.UserLoggedInEvent;
import com.brugalibre.common.security.auth.login.UserLoggedInNotifier;
import com.brugalibre.common.security.auth.login.UserLoggedInObserver;
import com.brugalibre.common.security.auth.register.UserRegisteredObserver;
import com.brugalibre.common.security.rest.api.AuthController;
import com.brugalibre.common.security.rest.model.LoginRequest;
import com.brugalibre.common.security.rest.model.LoginResponse;
import com.brugalibre.common.security.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Objects.requireNonNull;

@Service
public class UserLoginService implements UserLoggedInNotifier {

   private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

   private final UserRepository userRepository;
   private final AuthenticationManager authenticationManager;
   private final JwtUtils jwtUtils;
   private final List<UserLoggedInObserver> userLoggedInObservers;

   public UserLoginService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
      this.authenticationManager = authenticationManager;
      this.userRepository = userRepository;
      this.jwtUtils = jwtUtils;
      this.userLoggedInObservers = new ArrayList<>();
   }

   /**
    * Try to authenticate a {@link User} for the given {@link LoginRequest}
    * Also all {@link UserRegisteredObserver} are notified if the user was registered successfully
    *
    * @param loginRequest the {@link LoginRequest}
    * @return a {@link LoginResponse} with details about the logged-in user
    */
   public LoginResponse authenticateUser(LoginRequest loginRequest) {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String accessToken = jwtUtils.generateJwtToken((UserDetails) authentication.getPrincipal());

      User user = (User) authentication.getPrincipal();
      String phoneNr = getPhoneNr(user);
      notifyUserLoggedInObservers(user);
      LOG.info("User '" + user.getId() + "' authenticated!");
      return new LoginResponse(user.getId(),
              user.getUsername(),
              phoneNr,
              accessToken,
              user.getRoles());
   }

   private void notifyUserLoggedInObservers(User user) {
      UserLoggedInEvent userRegisteredEvent = UserLoggedInEvent.of(user);
      userLoggedInObservers.forEach(handleUserLoggedIn(userRegisteredEvent));
   }

   private Consumer<UserLoggedInObserver> handleUserLoggedIn(UserLoggedInEvent userRegisteredEvent) {
      return userLoggedInObserver -> {
         try {
            userLoggedInObserver.userLoggedIn(userRegisteredEvent);
         } catch (Exception e) {
            String errorText = TextFormatter.formatText("Exception while notifying user-logged-in-observer {}", new Object[]{userLoggedInObserver});
            LOG.error(errorText, e);
         }
      };
   }

   @Override
   public void addUserRegisteredObserver(UserLoggedInObserver userLoggedInObserver) {
      this.userLoggedInObservers.add(requireNonNull(userLoggedInObserver));
   }

   private String getPhoneNr(User user) {
      com.brugalibre.domain.user.model.User domainUser = userRepository.getById(user.getId());
      return domainUser.getMobilePhone().getPhoneNr();
   }

}
