package com.brugalibre.domain.user.mapper;


import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.domain.contactpoint.mapper.ContactPointEntityMapper;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.persistence.contactpoint.mobilephone.MobilePhoneEntity;
import com.brugalibre.persistence.user.UserEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = ContactPointEntityMapper.class)
public interface UserEntityMapper extends CommonDomainModelMapper<User, UserEntity> {

   @Mapping(target = "roles", ignore = true)
   @Override
   UserEntity map2DomainEntity(User user);

   @AfterMapping
   default void afterMapping(@MappingTarget UserEntity userEntity) {
      userEntity.getContactPoints()
              .forEach(contactPointEntity -> contactPointEntity.setUser(userEntity));
   }
}