package com.brugalibre.common.rest.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record MobilePhoneChangeResponse(String data) {

}
