package com.brugalibre.notification.send.email.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.config.email.EMailSendConfig;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EMailSendServiceImplTest {

   @Captor
   ArgumentCaptor<Message> emailCaptor;

   @Test
   void sendEMailAlert() throws AlertSendException, MessagingException, IOException {
      // Given
      String title = "Test Email";
      String msg = "This is a test email sent from EMailSendServiceImplTest.";
      String receiver = "test@receiver.com";
      String sender = "sender@receiver.com";
      AlertSendInfos infos = new AlertSendInfos(AlertType.EMAIL, title, msg, List.of(receiver));
      InternetAddress[] expectedFrom = {new InternetAddress(sender)};
      InternetAddress[] expectedTo = {new InternetAddress(receiver)};

      EMailSendServiceImpl eMailSendService = spy(new EMailSendServiceImpl());
      doNothing().when(eMailSendService).send(any());
      AlertSendConfig config = createAlertSendConfig(sender);

      // Then
      eMailSendService.sendAlert(config, infos);

      // When
      verify(eMailSendService).send(emailCaptor.capture());
      Message message = emailCaptor.getValue();
      assertEquals(infos.title(), message.getSubject());
      assertEquals(infos.msg(), message.getContent());
      assertEquals(1, message.getFrom().length);
      assertEquals(expectedFrom[0].toString(), message.getFrom()[0].toString());
      assertEquals(1, message.getAllRecipients().length);
      assertEquals(expectedTo[0].toString(), message.getAllRecipients()[0].toString());
   }

   private static AlertSendConfig createAlertSendConfig(String sender) {
      AlertSendConfig config = new AlertSendConfig();
      EMailSendConfig eMailSendConfig = new EMailSendConfig();
      eMailSendConfig.setSmtpHost("smtp.test.com");
      eMailSendConfig.setSender(sender);
      eMailSendConfig.setSmptSocketFactoryClass("socket-class");
      config.seteMailSendConfig(eMailSendConfig);
      return config;
   }
}