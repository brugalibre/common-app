package com.brugalibre.common.security.user.model.register;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record RegisterRequest(@NotNull String username, @NotNull String password, String userPhoneNr,
                              @NotNull List<String> roles) {
   // no-op
}
