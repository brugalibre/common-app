package com.brugalibre.common.rest.api.mobilephone;

import com.brugalibre.common.rest.model.ChangeMobilePhoneRequest;
import com.brugalibre.common.rest.model.MobilePhoneChangeResponse;
import com.brugalibre.common.rest.service.MobilePhoneChangeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mobilephone")
public class MobilePhoneController {

   private final MobilePhoneChangeService mobilePhoneChangeService;

   @Autowired
   public MobilePhoneController(MobilePhoneChangeService mobilePhoneChangeService) {
      this.mobilePhoneChangeService = mobilePhoneChangeService;
   }

   @PostMapping("/change")
   public ResponseEntity<?> changeMobilePhone(@Valid @RequestBody ChangeMobilePhoneRequest changeMobilePhoneRequest) {
      MobilePhoneChangeResponse mobilePhoneChangeResponse = mobilePhoneChangeService.changeMobilePhone(changeMobilePhoneRequest);
      return ResponseEntity.ok(mobilePhoneChangeResponse);
   }
}