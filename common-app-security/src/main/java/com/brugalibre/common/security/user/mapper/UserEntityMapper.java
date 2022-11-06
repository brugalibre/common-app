package com.brugalibre.common.security.user.mapper;

import com.brugalibre.common.security.user.model.User;
import com.brugalibre.persistence.user.Role;
import com.brugalibre.persistence.user.UserEntity;
import org.mapstruct.*;
import org.springframework.security.core.GrantedAuthority;

@Mapper
public interface UserEntityMapper {

   @Mapping(target = "roles", ignore = true)
   UserEntity map2UserEntity(User user);

   @AfterMapping
   default void mapAuthorities2Roles(@MappingTarget UserEntity userEntity, User user) {
      userEntity.setRoles(user.getAuthorities()
              .stream()
              .map(GrantedAuthority::getAuthority)
              .map(Role::valueOf)
              .toList());
   }

   /**
    * We have to do de mapping manually, since the {@link org.springframework.security.core.userdetails.User} is immutable,
    * but we have to map the authority-values
    */
   default User map2User(UserEntity userEntity) {

      return new User(userEntity);
   }
}