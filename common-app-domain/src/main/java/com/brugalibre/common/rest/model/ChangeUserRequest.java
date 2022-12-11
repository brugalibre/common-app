package com.brugalibre.common.rest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ChangeUserRequest(@Valid String username,@Valid String newUserPhoneNr) {
   // no-op
}
