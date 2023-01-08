package com.brugalibre.domain.user.service.userrole;

import com.brugalibre.domain.file.service.YamlService;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.repository.UserRepository;
import com.brugalibre.domain.user.service.UserRoleService;
import com.brugalibre.persistence.user.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleConfigService {

   private static final Logger LOG = LoggerFactory.getLogger(UserRoleConfigService.class);
   private final UserRoleService userRoleService;
   private final UserRepository userRepository;
   private final YamlService yamlService;
   private final String configuredUserRolesConfigFile;

   @Autowired
   public UserRoleConfigService(UserRoleService userRoleService, UserRepository userRepository, YamlService yamlService) {
      this.userRoleService = userRoleService;
      this.userRepository = userRepository;
      this.yamlService = yamlService;
      this.configuredUserRolesConfigFile = UserRoleConfig.PATH_TO_CONFIGURED_USER_ROLES;
   }

   /**
    * Adds all missing roles which are configured in the {@link UserRoleConfig#PATH_TO_CONFIGURED_USER_ROLES} and which the given User does not already have
    *
    * @param userId the id of the {@link User} whose {@link Role}s are checked and updated
    * @return an instance of the changed {@link User}
    */
   public User addMissingRoles(String userId) {
      UserRoleConfig userRoleConfig = yamlService.readYaml(configuredUserRolesConfigFile, UserRoleConfig.class);
      User user = userRepository.getById(userId);
      User changedUser = user;
      LOG.info("Checking user [{}] with user-role-config [{}]", user.id(), userRoleConfig);
      if (userRoleConfig.contains(user.username())) {
         List<Role> roles = userRoleConfig.getRoles(user.username());
         LOG.info("Adding roles {} for user [{}]", roles, user.id());
         changedUser = userRoleService.addMissingRolesIfNecessary(userId, roles);
      }
      return changedUser;
   }
}
