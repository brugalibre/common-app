package com.brugalibre.notification.send.common.model;

import com.brugalibre.notification.api.v1.alerttype.AlertType;

import java.util.List;

public record AlertSendInfos(AlertType alertType, String title, String msg, List<String> receivers) {
   // no-op
}
