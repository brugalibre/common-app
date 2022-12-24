package com.brugalibre.domain.contactpoint.mobilephone.mapper;


import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.domain.contactpoint.model.ContactPoint;
import com.brugalibre.persistence.contactpoint.ContactPointEntity;
import com.brugalibre.persistence.contactpoint.mobilephone.MobilePhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

@Mapper
public interface MobilePhoneEntityMapper extends CommonDomainModelMapper<MobilePhone, MobilePhoneEntity> {

   @Mapping(target = "userId", source = "mobilePhoneEntity.user.id")
   @Override
   MobilePhone map2DomainModel(MobilePhoneEntity mobilePhoneEntity);

   @Mapping(target = "user", ignore = true)
   @Override
   MobilePhoneEntity map2DomainEntity(MobilePhone domainModel);
}