package com.brugalibre.common.security.rest.api;

import com.brugalibre.common.security.auth.JwtUtils;
import com.brugalibre.common.security.auth.register.UserRegisteredEvent;
import com.brugalibre.common.security.auth.register.UserRegisteredNotifier;
import com.brugalibre.common.security.auth.register.UserRegisteredObserver;
import com.brugalibre.common.security.rest.model.JwtResponse;
import com.brugalibre.common.security.rest.model.LoginRequest;
import com.brugalibre.common.security.rest.model.MessageResponse;
import com.brugalibre.common.security.rest.model.RegisterRequest;
import com.brugalibre.common.security.user.model.User;
import com.brugalibre.common.security.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController implements UserRegisteredNotifier {
   private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

   private final AuthenticationManager authenticationManager;

   private final UserRepository userRepository;

   private final PasswordEncoder encoder;

   private final JwtUtils jwtUtils;

   private final List<UserRegisteredObserver> userRegisteredObservers;

   @Autowired
   public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
      this.authenticationManager = authenticationManager;
      this.userRepository = userRepository;
      this.encoder = encoder;
      this.jwtUtils = jwtUtils;
      this.userRegisteredObservers = new ArrayList<>();
   }

   @PostMapping("/login")
   public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String accessToken = jwtUtils.generateJwtToken(authentication);

      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      List<String> roles = User.mapAuthorities2Roles(userDetails.getAuthorities());
      LOG.info("User '" + userDetails.getUsername() + "' authenticated!");
      return ResponseEntity.ok(new JwtResponse(accessToken,
              userDetails.getUsername(), roles));
   }

   @PostMapping("/register")
   public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
      if (userRepository.existsUserByUsername(registerRequest.username())) {
         throw new UserAlreadyExistsException("Username is already taken!");
      }

      // Create new user's account
      User user = new User(registerRequest.username(),
              encoder.encode(registerRequest.password()), registerRequest.map2Role());
      userRepository.save(user);
      notifyUserRegisteredObservers(registerRequest);
      return ResponseEntity.ok(new MessageResponse("Registration successful!"));
   }

   private void notifyUserRegisteredObservers(RegisterRequest registerRequest) {
      this.userRegisteredObservers.forEach(userRegisteredObserver -> userRegisteredObserver.userRegistered(new UserRegisteredEvent(registerRequest)));
   }

   @Override
   public void addUserRegisteredObserver(UserRegisteredObserver userRegisteredObserver) {
      this.userRegisteredObservers.add(requireNonNull(userRegisteredObserver));
   }
}