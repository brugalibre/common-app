package com.brugalibre.common.rest.service;

import com.brugalibre.i18n.TextResources;
import com.brugalibre.common.rest.model.ChangeUserRequest;
import com.brugalibre.common.rest.model.UserChangeResponse;
import com.brugalibre.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserChangeService {
   private final UserRepository userRepository;

   @Autowired
   public UserChangeService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   public UserChangeResponse changeUser(ChangeUserRequest changeUserRequest) {
      userRepository.updatePhoneNr(changeUserRequest.username(), changeUserRequest.newUserPhoneNr());
      return new UserChangeResponse(TextResources.USER_SUCCESSFULY_CHANGED);
   }
}
