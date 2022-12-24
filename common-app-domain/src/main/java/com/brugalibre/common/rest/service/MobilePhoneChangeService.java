package com.brugalibre.common.rest.service;

import com.brugalibre.domain.contactpoint.mobilephone.repository.MobilePhoneRepository;
import com.brugalibre.i18n.TextResources;
import com.brugalibre.common.rest.model.ChangeMobilePhoneRequest;
import com.brugalibre.common.rest.model.MobilePhoneChangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobilePhoneChangeService {
   private final MobilePhoneRepository mobilePhoneRepository;

   @Autowired
   public MobilePhoneChangeService(MobilePhoneRepository mobilePhoneRepository) {
      this.mobilePhoneRepository = mobilePhoneRepository;
   }

   public MobilePhoneChangeResponse changeMobilePhone(ChangeMobilePhoneRequest changeMobilePhoneRequest) {
      mobilePhoneRepository.updatePhoneNr(changeMobilePhoneRequest.userId(), changeMobilePhoneRequest.newUserPhoneNr());
      return new MobilePhoneChangeResponse(TextResources.USER_SUCCESSFULY_CHANGED);
   }
}
