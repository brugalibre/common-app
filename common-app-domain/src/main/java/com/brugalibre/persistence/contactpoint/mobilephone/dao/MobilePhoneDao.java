package com.brugalibre.persistence.contactpoint.mobilephone.dao;

import com.brugalibre.persistence.contactpoint.mobilephone.MobilePhoneEntity;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface MobilePhoneDao extends CrudRepository<MobilePhoneEntity, String> {

   /**
    * Updates the phone-number for the user with the given username
    *
    * @param newPhoneNumber the new phone-number
    * @param userId         the id of the user which owns the mobile-phone to update
    */
   @Transactional
   @Modifying(flushAutomatically = true, clearAutomatically = true)
   @Query("UPDATE MobilePhoneEntity mobilePhone SET mobilePhone.phoneNr = ?1 WHERE mobilePhone.user.id = ?2")
   void updatePhoneNr(@NotBlank String newPhoneNumber, @NotBlank String userId);
}
