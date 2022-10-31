package com.brugalibre.domain.user.mapper;


import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.persistence.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserEntityMapper extends CommonDomainModelMapper<User, UserEntity> {

   @Mapping(target = "roles", ignore = true)
   @Override
   UserEntity map2DomainEntity(User user);
}