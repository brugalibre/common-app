package com.brugalibre.domain.contactpoint.mapper;


import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.domain.contactpoint.ContactPointType;
import com.brugalibre.domain.contactpoint.mobilephone.mapper.MobilePhoneEntityMapper;
import com.brugalibre.domain.contactpoint.mobilephone.mapper.MobilePhoneEntityMapperImpl;
import com.brugalibre.domain.contactpoint.mobilephone.model.MobilePhone;
import com.brugalibre.domain.contactpoint.model.ContactPoint;
import com.brugalibre.persistence.contactpoint.ContactPointEntity;
import com.brugalibre.persistence.contactpoint.mobilephone.MobilePhoneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

@Mapper(uses = MobilePhoneEntityMapper.class)
public interface ContactPointEntityMapper extends CommonDomainModelMapper<ContactPoint, ContactPointEntity> {

   @Mapping(target = "user", ignore = true)
   @Override
   ContactPointEntity map2DomainEntity(ContactPoint domainModel);

   @ObjectFactory
   default <T extends ContactPoint> T resolve(ContactPointEntity sourceContactPointEntity, @TargetType Class<T> type) {
      if (sourceContactPointEntity.getContactPointType() == ContactPointType.MOBILE_PHONE) {
         return (T) new MobilePhoneEntityMapperImpl().map2DomainModel((MobilePhoneEntity) sourceContactPointEntity);
      }
      throw new IllegalStateException("Unknown contact-point type '" + sourceContactPointEntity.getContactPointType() + "'");
   }

   @ObjectFactory
   default <T extends ContactPointEntity> T resolve(ContactPoint sourceContactPoint, @TargetType Class<T> type) {
      if (sourceContactPoint.getContactPointType() == ContactPointType.MOBILE_PHONE) {
         return (T) new MobilePhoneEntityMapperImpl().map2DomainEntity((MobilePhone) sourceContactPoint);
      }
      throw new IllegalStateException("Unknown contact-point type '" + sourceContactPoint.getContactPointType() + "'");
   }
}