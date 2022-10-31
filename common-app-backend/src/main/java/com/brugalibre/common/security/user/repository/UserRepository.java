package com.brugalibre.common.security.user.repository;


import com.brugalibre.common.security.user.model.User;
import com.brugalibre.persistence.user.UserDao;
import com.brugalibre.persistence.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserRepository implements UserDetailsService {
   private final UserDao userDao;

   @Autowired
   public UserRepository(UserDao userDao) {
      this.userDao = userDao;
   }

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return getOptionalUserDetails(username)
              .orElseThrow(() -> new IllegalStateException("No User found for username '" + username + "'"));
   }

   public boolean existsUserByUsername(String username) {
      return getOptionalUserDetails(username)
              .isPresent();
   }

   public void save(org.springframework.security.core.userdetails.User user) {
      this.userDao.save(new UserEntity(user.getUsername(), user.getPassword(), User.toRoles(user.getAuthorities())));
   }

   @NotNull
   private Optional<UserDetails> getOptionalUserDetails(String username) {
      return userDao.findByUsername(username)
              .map(User::new);
   }
}
