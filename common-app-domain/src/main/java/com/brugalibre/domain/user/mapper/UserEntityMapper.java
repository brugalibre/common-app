package com.brugalibre.domain.user.mapper;


import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.domain.contactpoint.mapper.ContactPointEntityMapper;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.domain.contactpoint.model.ContactPoint;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.domain.user.model.yaml.UserYamlEntry;
import com.brugalibre.persistence.user.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(uses = ContactPointEntityMapper.class)
public interface UserEntityMapper extends CommonDomainModelMapper<User, UserEntity> {

   List<User> map2Users(List<UserYamlEntry> userYamlInputs);

   @Mapping(target = "id", ignore = true)
   @Mapping(source = "phoneNr", target = "contactPoints", qualifiedByName = "mapPhoneNrToContactPoint")
   User map2User(UserYamlEntry userYamlEntry);

   @Named("mapPhoneNrToContactPoint")
   default List<ContactPoint> mapPhoneNrToContactPoint(String phoneNr) {
      return List.of(MobilePhone.of(phoneNr));
   }

   @AfterMapping
   default void afterMapping(@MappingTarget UserEntity userEntity) {
      userEntity.getContactPoints()
              .forEach(contactPointEntity -> contactPointEntity.setUser(userEntity));
   }
}