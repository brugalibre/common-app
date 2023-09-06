package com.brugalibre.notification.send.common.model;

import java.util.List;

public record AlertSendInfos(String title, String msg, List<String> receivers) {
   // no-op
}
