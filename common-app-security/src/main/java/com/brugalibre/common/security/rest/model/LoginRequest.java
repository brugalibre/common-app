package com.brugalibre.common.security.rest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotNull;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record LoginRequest(@NotNull String username, @NotNull String password) {
}
