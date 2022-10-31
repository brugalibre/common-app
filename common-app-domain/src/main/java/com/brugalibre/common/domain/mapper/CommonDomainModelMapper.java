package com.brugalibre.common.domain.mapper;

import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.common.domain.persistence.DomainEntity;

import java.util.List;

/**
 * Interface for declaring a mapper from a {@link DomainModel} to a {@link DomainEntity} and vice versa
 *
 * @param <D> the type of {@link DomainModel}
 * @param <E> the type of {@link DomainEntity}
 */
public interface CommonDomainModelMapper<D extends DomainModel, E extends DomainEntity> {

   /**
    * Maps from a {@link DomainEntity} to a {@link DomainModel}
    *
    * @param domainEntity the {@link DomainEntity} to map
    * @return a mapped {@link DomainModel}
    */
   D map2DomainModel(E domainEntity);

   /**
    * Maps from a list with DomainEntities to a list with {@link DomainModel}s
    *
    * @param domainEntities the {@link DomainEntity} to map
    * @return a list with mapped domain models
    */
   List<D> map2DomainModels(List<E> domainEntities);

   /**
    * Maps from a {@link DomainModel} to a {@link DomainEntity}
    *
    * @param domainModel the {@link DomainModel} to map
    * @return a mapped {@link DomainEntity}
    */
   E map2DomainEntity(D domainModel);

   /**
    * Maps the given list with {@link DomainModel}s into their corresponding {@link DomainEntity}
    *
    * @param domainModels the {@link DomainModel}s to map
    * @return a list with mapped DomainEntities
    */
   List<E> map2DomainEntities(List<D> domainModels);
}
