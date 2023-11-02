package com.brugalibre.common.security.user.model.passwordchange;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotNull;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record UserPasswordChangeRequest(@NotNull String userId, @NotNull String newPassword) {
   // no-op
}
