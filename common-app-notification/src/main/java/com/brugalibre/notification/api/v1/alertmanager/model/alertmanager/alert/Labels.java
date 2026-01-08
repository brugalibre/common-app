package com.brugalibre.notification.api.v1.alertmanager.model.alertmanager.alert;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Labels(String alertname, String instance, String severity) {
}
