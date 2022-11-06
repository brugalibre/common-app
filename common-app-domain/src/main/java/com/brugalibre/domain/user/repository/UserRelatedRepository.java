package com.brugalibre.domain.user.repository;

import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.common.domain.repository.CommonDomainRepository;
import com.brugalibre.common.domain.repository.NoDomainModelFoundException;
import com.brugalibre.domain.user.model.User;

/**
 * A {@link CommonDomainRepository} related to a {@link User}. Meaning that this repository contains method for retrieving
 * domain objects which belongs to a certain user.
 *
 * @param <D> type of the {@link DomainModel} this repository obtains
 */
public interface UserRelatedRepository<D extends DomainModel> extends CommonDomainRepository<D> {
   /**
    * Returns an entity from type {@link D} which belongs to the given user id
    *
    * @param userId the id of the {@link User}
    * @return an entity from type {@link D} which belongs to the given user id
    * @throws NoDomainModelFoundException if there is no {@link D} associated with the given user-id
    */
   D getByUserId(String userId);
}
