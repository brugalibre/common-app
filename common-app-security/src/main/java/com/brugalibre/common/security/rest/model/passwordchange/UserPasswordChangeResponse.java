package com.brugalibre.common.security.rest.model.passwordchange;


import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record UserPasswordChangeResponse(String message) {
}
