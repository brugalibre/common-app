package com.brugalibre.notification.send.email.service;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.config.email.EMailSendConfig;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.notification.send.common.model.CommonAlertSendResponse;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Authenticator;
import jakarta.mail.Transport;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

public class EMailSendServiceImpl implements AlertSendService {
   private static final Logger LOGGER = LoggerFactory.getLogger(EMailSendServiceImpl.class);

   @Override
   public AlertSendResponse sendAlert(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) throws AlertSendException {
      EMailSendConfig eMailSendConfig = alertSendConfig.getEMailSendConfig();
      Properties prop = getEMailProperties(eMailSendConfig);
      Session session = createSessionWithAuthenticator(eMailSendConfig, prop);
      return sendMessage(alertSendInfos, session, alertSendInfos.receivers(), eMailSendConfig.getSender());
   }

   private AlertSendResponse sendMessage(AlertSendInfos alertSendInfos, Session session,
                                         List<String> receivers, String sender) throws AlertSendException {
      try {
         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress(sender));
         message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(String.join(",", receivers)));
         message.setSubject(alertSendInfos.title());
         message.setText(alertSendInfos.msg());
         send(message);
         return new CommonAlertSendResponse(200, "Ok");
      } catch (MessagingException e) {
         LOGGER.error("Error while sending email to '{}'", receivers, e);
         throw new AlertSendException(e);
      }
   }

   protected void send(Message message) throws MessagingException {
      Transport.send(message);
   }

   private Session createSessionWithAuthenticator(EMailSendConfig eMailSendConfig, Properties prop) {
      return Session.getInstance(prop,
              new Authenticator() {
                 protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(eMailSendConfig.getUsername(), String.valueOf(eMailSendConfig.getApiKeyProvider().get()));
                 }
              });
   }

   private static Properties getEMailProperties(EMailSendConfig eMailSendConfig) {
      Properties prop = new Properties();
      prop.put("mail.smtp.host", eMailSendConfig.getSmtpHost());
      prop.put("mail.smtp.port", eMailSendConfig.getSmtpPort());
      prop.put("mail.smtp.auth", "true");
      prop.put("mail.smtp.socketFactory.port", eMailSendConfig.getSmptSocketFactoryPort());
      prop.put("mail.smtp.socketFactory.class", eMailSendConfig.getSmptSocketFactoryClass());
      return prop;
   }
}
