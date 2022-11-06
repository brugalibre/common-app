package com.brugalibre.common.security.user.service;

import com.brugalibre.common.security.user.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserProviderImpl implements IUserProvider {

   @Override
   public com.brugalibre.domain.user.model.User getCurrentUser() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      User user = (User) authentication.getPrincipal();
      // Map to domain-user, so we don't have to expose spring-security to other modules
      return com.brugalibre.domain.user.model.User.of(user.getId(), user.getUsername(), user.getPassword(), user.getPhoneNr());
   }

   @Override
   public String getCurrentUserId() {
      return getCurrentUser().id();
   }
}
