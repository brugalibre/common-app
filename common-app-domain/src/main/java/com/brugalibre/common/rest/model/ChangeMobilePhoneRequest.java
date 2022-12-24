package com.brugalibre.common.rest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.constraints.NotBlank;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ChangeMobilePhoneRequest(@NotBlank String userId, @NotBlank String newUserPhoneNr) {
   // no-op
}
