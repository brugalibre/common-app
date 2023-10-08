package com.brugalibre.common.security.user.service;

import com.brugalibre.common.security.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProviderImpl implements IUserProvider {

   private static final Logger LOG = LoggerFactory.getLogger(UserProviderImpl.class);
   private final UserRepository userRepository;

   public UserProviderImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public com.brugalibre.domain.user.model.User getCurrentUser() {
      // Map to domain-user, so we don't have to expose spring-security to other modules
      return getUser()
              .map(User::getId)
              .map(userRepository::getById)
              .orElse(null);
   }

   private static Optional<User> getUser() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      User user = null;
      if (authentication.getPrincipal() instanceof User) {
         user = (User) authentication.getPrincipal();
      }
      return Optional.ofNullable(user);
   }

   @Override
   public String getCurrentUserId() {
      return getUser()
              .map(User::getId)
              .orElse(null);
   }
}
