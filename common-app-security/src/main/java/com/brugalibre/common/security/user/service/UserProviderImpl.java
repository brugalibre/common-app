package com.brugalibre.common.security.user.service;

import com.brugalibre.common.security.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserProviderImpl implements IUserProvider {

   private final UserRepository userRepository;

   public UserProviderImpl(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   public com.brugalibre.domain.user.model.User getCurrentUser() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      User user = (User) authentication.getPrincipal();
      // Map to domain-user, so we don't have to expose spring-security to other modules
      return userRepository.getById(user.getId());
   }

   @Override
   public String getCurrentUserId() {
      return getCurrentUser().id();
   }
}
