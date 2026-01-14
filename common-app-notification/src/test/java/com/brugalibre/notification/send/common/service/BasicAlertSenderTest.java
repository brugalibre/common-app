package com.brugalibre.notification.send.common.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BasicAlertSenderTest {

   @Test
   void sendMessage() throws AlertSendException {
      // Given
      List<String> receivers = List.of("receiver");
      String title = "Title";
      String message = "Message";
      AlertSendConfig alertSendConfig = new AlertSendConfig();
      AlertSendInfos alertSendInfos = new AlertSendInfos(AlertType.CLICK_SEND_SMS, title, message, receivers);
      AlertSendService alertSendService = mock(AlertSendService.class);
      BasicAlertSender applicationErrorAlertSender = new BasicAlertSender(() -> alertSendConfig, alertType -> alertSendService);

      // When
      applicationErrorAlertSender.sendMessage(alertSendInfos);

      // Then
      verify(alertSendService).sendAlert(eq(alertSendConfig), eq(alertSendInfos));
   }
}