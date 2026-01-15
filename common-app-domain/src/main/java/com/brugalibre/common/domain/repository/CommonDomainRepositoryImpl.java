package com.brugalibre.common.domain.repository;

import com.brugalibre.common.domain.mapper.CommonDomainModelMapper;
import com.brugalibre.common.domain.model.DomainModel;
import com.brugalibre.common.domain.persistence.DomainEntity;
import com.brugalibre.common.domain.text.TextFormatter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

/**
 * Base implementation for all {@link CommonDomainRepository} for a {@link DomainModel} type
 * A {@link CommonDomainRepository} contains also a {@link CrudRepository} for the specific {@link DomainEntity} this repository
 * covers.
 * <p>
 * It therefore takes care for the mapping between the entity and the domain model in order to load, insert or update the
 * corresponding db-table
 *
 * @param <D> the type of {@link DomainModel}
 * @param <E> the type of {@link DomainEntity}
 * @param <R> the type of {@link CrudRepository}
 */
public abstract class CommonDomainRepositoryImpl<D extends DomainModel, E extends DomainEntity,
        R extends CrudRepository<E, String>> implements CommonDomainRepository<D> {
   protected final CommonDomainModelMapper<D, E> domainModelMapper;
   protected final R domainDao;

   public CommonDomainRepositoryImpl(R domainDao, CommonDomainModelMapper<D, E> domainModelMapper) {
      this.domainDao = domainDao;
      this.domainModelMapper = domainModelMapper;
   }

   @Override
   public D save(D domainModel) {
      E domainEntity = domainDao.save(domainModelMapper.map2DomainEntity(domainModel));
      return domainModelMapper.map2DomainModel(domainEntity);
   }

   @Override
   public void saveAll(List<D> domainModels) {
      domainDao.saveAll(domainModelMapper.map2DomainEntities(domainModels));
   }

   /**
    * Returns the {@link DomainModel} associated to the given id
    *
    * @param id the id to find the domain model
    * @return the {@link DomainModel} associated to the given id
    * @throws NoDomainModelFoundException if there is no entity associated with the given id
    */
   @Override
   public D getById(String id) {
      E domainEntity = getByIdOrThrow(id);
      return domainModelMapper.map2DomainModel(domainEntity);
   }

   @Override
   public List<D> getAll() {
      return StreamSupport.stream(domainDao.findAll().spliterator(), false)
              .map(domainModelMapper::map2DomainModel)
              .toList();
   }

   @Override
   public void deleteAll() {
      domainDao.deleteAll();
   }

   @Override
   public void deleteById(String id) {
      domainDao.deleteById(id);
   }

   protected D getEntityAndMap(Supplier<E> entitySupplier, String exceptionTemplate, Object... args) {
      E domainEntity = entitySupplier.get();
      if (isNull(domainEntity)) {
         throw new NoDomainModelFoundException(TextFormatter.formatText(exceptionTemplate, args));
      }
      return this.domainModelMapper.map2DomainModel(domainEntity);
   }

   /**
    * Returns the {@link DomainModel} for the given id or throws an {@link NoDomainModelFoundException}
    *
    * @param id the id
    * @return the {@link DomainModel} for the given id or throws an {@link NoDomainModelFoundException}
    * @throws NoDomainModelFoundException if there is no entity associated with the given id
    */
   protected E getByIdOrThrow(String id) {
      return domainDao.findById(id)
              .orElseThrow(() -> new NoDomainModelFoundException("No object found by id {}", id));
   }
}
