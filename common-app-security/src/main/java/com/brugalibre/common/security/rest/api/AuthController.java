package com.brugalibre.common.security.rest.api;

import com.brugalibre.common.security.rest.model.*;
import com.brugalibre.common.security.rest.model.passwordchange.UserPasswordChangeRequest;
import com.brugalibre.common.security.rest.model.passwordchange.UserPasswordChangeResponse;
import com.brugalibre.common.security.rest.service.UserLoginService;
import com.brugalibre.common.security.rest.service.passwordchange.UserUserPasswordChangeService;
import com.brugalibre.common.security.rest.service.UserRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

   private final UserRegisterService userRegisterService;
   private final UserUserPasswordChangeService userPasswordChangeService;
   private final UserLoginService userLoginService;

   @Autowired
   public AuthController(UserRegisterService userRegisterService, UserLoginService userLoginService,
                         UserUserPasswordChangeService userPasswordChangeService) {
      this.userLoginService = userLoginService;
      this.userRegisterService = userRegisterService;
      this.userPasswordChangeService = userPasswordChangeService;
   }

   @PostMapping("/login")
   public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
      LoginResponse loginResponse = this.userLoginService.authenticateUser(loginRequest);
      return ResponseEntity.ok(loginResponse);
   }

   @PostMapping("/register")
   public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
      RegisterResponse registerResponse = userRegisterService.registerUser(registerRequest);
      return ResponseEntity.ok(registerResponse);
   }

   @PutMapping("/changedPassword")
   public ResponseEntity<?> changePassword(@Valid @RequestBody UserPasswordChangeRequest userPasswordChangeRequest) {
      UserPasswordChangeResponse userPasswordChangeResponse = userPasswordChangeService.changeUserPassword(userPasswordChangeRequest);
      return ResponseEntity.ok(userPasswordChangeResponse);
   }
}