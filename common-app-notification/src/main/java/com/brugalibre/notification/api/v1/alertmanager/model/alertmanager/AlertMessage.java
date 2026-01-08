package com.brugalibre.notification.api.v1.alertmanager.model.alertmanager;


import com.brugalibre.notification.api.v1.alertmanager.model.alertmanager.alert.Alert;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record AlertMessage(String version,
                           String groupKey,
                           int truncatedAlerts,
                           String status,
                           String receiver,
                           Object groupLabels,
                           Object commonLabels,
                           Object commonAnnotations,
                           String externalURL,
                           List<Alert> alerts) {
}
