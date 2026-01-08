package com.brugalibre.notification.api.v1.alertmanager.model.alertmanager.alert;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record Alert(String status,
                    Labels labels,
                    Annotations annotations,
                    String startsAt,
                    String endsAt,
                    String generatorUrl,
                    String fingerprint) {
}
