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

    /**
     * Changes the {@link User}s password
     *
     * @param newPassword the new password value to set
     * @param userId      the id of the User whose password has to be changed
     */
    public void changePassword(String newPassword, String userId) {
        domainDao.setPassword(newPassword, userId);
    }

    /**
     * Changes the {@link User}s username
     *
     * @param newUsername the new username value to set
     * @param userId      the id of the User whose name has to be changed
     */
    public void changeUsername(String newUsername, String userId) {
        domainDao.setUsername(newUsername, userId);
    }
}
