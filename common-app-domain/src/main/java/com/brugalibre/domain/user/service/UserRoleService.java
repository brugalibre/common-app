package com.brugalibre.domain.user.service;

import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.persistence.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

   private final UserRepository userRepository;

   @Autowired
   public UserRoleService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   /**
    * From the given List with {@link Role}s all are added which the {@link User} with the given id is missing right now
    *
    * @param userId the id of the {@link User} whose {@link Role} are checked and updated
    * @param roles  the {@link Role}s to add
    * @return an instance of the changed {@link User}
    */
   public User addMissingRolesIfNecessary(String userId, List<Role> roles) {
      User user = this.userRepository.getById(userId);
      User changedUser = user;
      for (Role role : roles) {
         if (!user.hasRole(role)) {
            changedUser = user.addRole(role);
            userRepository.save(changedUser);
         }
      }
      return changedUser;
   }
}
