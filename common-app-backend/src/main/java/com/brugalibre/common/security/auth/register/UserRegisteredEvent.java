package com.brugalibre.common.security.auth.register;

import com.brugalibre.common.security.rest.model.RegisterRequest;

public record UserRegisteredEvent(RegisterRequest registerRequest) {

}
