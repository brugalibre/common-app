package com.brugalibre.common.security.user.model.login;

import com.brugalibre.persistence.user.Role;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record LoginResponse(String userId, String username, String phoneNr, String accessToken, List<Role> roles) {
   // no-op
}
