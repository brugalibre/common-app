package com.brugalibre.common.security.rest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record JwtResponse(String accessToken, String username, List<String> roles) {
   // no-op
}
