package com.brugalibre.notification.send;

import java.util.List;

public record AlertSendInfos(String msg, List<String> receivers) {
   // no-op
}