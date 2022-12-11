package com.brugalibre.common.security.rest.api;

import com.brugalibre.common.security.rest.model.LoginRequest;
import com.brugalibre.common.security.rest.model.LoginResponse;
import com.brugalibre.common.security.rest.model.RegisterRequest;
import com.brugalibre.common.security.rest.model.RegisterResponse;
import com.brugalibre.common.security.rest.service.UserLoginService;
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
   private final UserLoginService userLoginService;

   @Autowired
   public AuthController(UserRegisterService userRegisterService, UserLoginService userLoginService) {
      this.userLoginService = userLoginService;
      this.userRegisterService = userRegisterService;
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
}