package com.brugalibre.common.domain.repository;

import com.brugalibre.common.domain.model.DomainModel;

import java.util.List;

/**
 * Base class for all domain repositories
 *
 * @param <T> type of the {@link DomainModel} this repository obtains
 */
public interface CommonDomainRepository<T extends DomainModel> {

   /**
    * Saves the given {@link DomainModel} to the database and returns an updated version of itself
    *
    * @param domainModel the {@link DomainModel} instance to save
    * @return a persisted or updated version of the given {@link DomainModel}
    */
   T save(T domainModel);

   /**
    * Saves the given {@link DomainModel}s to the database and returns an updated version of them
    *
    * @param domainModels all {@link DomainModel}s instances to save
    */
   void saveAll(List<T> domainModels);

   /**
    * Get the {@link DomainModel} for the given id
    *
    * @param id the id to find the domain model
    * @return the found {@link DomainModel}
    * @throws NoDomainModelFoundException if there is no {@link DomainModel} associated to the given key
    */
   T getById(String id);

   /**
    * @return all {@link DomainModel}s this repository contains
    */
   List<T> getAll();

   /**
    * Deletes all domain entities of this {@link CommonDomainRepository}
    */
   void deleteAll();
}
