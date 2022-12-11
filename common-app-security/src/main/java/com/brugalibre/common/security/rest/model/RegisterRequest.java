package com.brugalibre.common.security.rest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record RegisterRequest(@NotNull String username, @NotNull String password, @NotNull String userPhoneNr,
                              @NotNull List<String> roles) {
   // no-op
}
