package com.brugalibre.domain.user.repository;


import com.brugalibre.common.domain.repository.CommonDomainRepositoryImpl;
import com.brugalibre.domain.user.mapper.UserEntityMapperImpl;
import com.brugalibre.domain.user.model.User;
import com.brugalibre.persistence.user.UserEntity;
import com.brugalibre.persistence.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepository extends CommonDomainRepositoryImpl<User, UserEntity, UserDao> {

   @Autowired
   public UserRepository(UserDao userDao) {
      super(userDao, new UserEntityMapperImpl());
   }

   /**
    * Finds an optional {@link User} by its username
    *
    * @param username the username
    * @return an {@link Optional} of an {@link User}
    */
   public Optional<User> findByUsername(String username) {
      return domainDao.findByUsername(username)
              .map(domainModelMapper::map2DomainModel);
   }
}
