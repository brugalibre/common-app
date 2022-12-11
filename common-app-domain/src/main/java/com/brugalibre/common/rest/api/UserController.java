package com.brugalibre.common.rest.api;

import com.brugalibre.common.rest.model.ChangeUserRequest;
import com.brugalibre.common.rest.model.UserChangeResponse;
import com.brugalibre.common.rest.service.UserChangeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

   private final UserChangeService userChangeService;

   @Autowired
   public UserController(UserChangeService userChangeService) {
      this.userChangeService = userChangeService;
   }

   @PostMapping("/change")
   public ResponseEntity<?> changeUser(@Valid @RequestBody ChangeUserRequest changeUserRequest) {
      UserChangeResponse userChangeResponse = userChangeService.changeUser(changeUserRequest);
      return ResponseEntity.ok(userChangeResponse);
   }
}