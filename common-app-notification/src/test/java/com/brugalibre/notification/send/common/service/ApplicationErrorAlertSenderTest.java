package com.brugalibre.notification.send.common.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationErrorAlertSenderTest {

   @Test
   void sendApplicationErrorMessage() throws AlertSendException {
      // Given
      List<String> receivers = List.of("receiver");
      String title = "Title";
      String message = "Message";
      AlertSendConfig alertSendConfig = new AlertSendConfig();
      alertSendConfig.setOnApplicationErrorReceivers(receivers);
      alertSendConfig.setOnApplicationErrorAlertType(AlertType.CLICK_SEND_SMS);
      AlertSendInfos alertSendInfos = new AlertSendInfos(AlertType.CLICK_SEND_SMS, title, message, receivers);
      AlertSendService alertSendService = mock(AlertSendService.class);
      ApplicationErrorAlertSender applicationErrorAlertSender = new ApplicationErrorAlertSender(() -> alertSendConfig, alertType -> alertSendService);

      // When
      applicationErrorAlertSender.sendApplicationErrorMessage(title, message);

      // Then
      verify(alertSendService).sendAlert(eq(alertSendConfig), eq(alertSendInfos));
   }
}