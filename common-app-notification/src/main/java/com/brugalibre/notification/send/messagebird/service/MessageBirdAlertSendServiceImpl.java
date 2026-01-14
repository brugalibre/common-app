package com.brugalibre.notification.send.messagebird.service;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.notification.send.common.model.CommonAlertSendResponse;
import com.messagebird.MessageBirdClient;
import com.messagebird.MessageBirdService;
import com.messagebird.MessageBirdServiceImpl;
import com.messagebird.exceptions.GeneralException;
import com.messagebird.exceptions.UnauthorizedException;
import com.messagebird.objects.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Sms alert API From MessageBird
 */
public class MessageBirdAlertSendServiceImpl implements AlertSendService {
   private static final Logger LOG = LoggerFactory.getLogger(MessageBirdAlertSendServiceImpl.class);

   @Override
   public AlertSendResponse sendAlert(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) throws AlertSendException {
      LOG.info("Sending text {} to {} receivers", alertSendInfos, alertSendInfos.receivers().size());
      MessageBirdService messageBirdService = new MessageBirdServiceImpl(String.valueOf(alertSendConfig.getSmsSendConfig().getApiKeyProvider()));
      MessageBirdClient messageBirdClient = new MessageBirdClient(messageBirdService);
      // convert String number into acceptable format
      List<BigInteger> receivers = alertSendInfos.receivers()
              .stream()
              .map(BigInteger::new)
              .collect(Collectors.toList());
      try {
         final MessageResponse response = messageBirdClient.sendMessage(alertSendConfig.getSmsSendConfig().getOriginator(), alertSendInfos.msg(), receivers);
         LOG.info("Sending text done, response [{}]", response);
         return new CommonAlertSendResponse(200, response.getBody());
      } catch (UnauthorizedException | GeneralException e) {
         throw new AlertSendException(e);
      }
   }
}
