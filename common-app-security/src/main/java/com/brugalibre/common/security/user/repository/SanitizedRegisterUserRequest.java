package com.brugalibre.common.security.user.repository;

import java.util.List;

public record SanitizedRegisterUserRequest(String username, String password, String userPhoneNr,
                                           List<String> roles){
}
