package com.brugalibre.notification.send.clicksend.service;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.config.sms.SmsSendConfig;
import com.brugalibre.notification.send.clicksend.model.ClickSendSmsMessage;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.notification.send.common.model.CommonAlertSendResponse;
import com.brugalibre.notification.util.AuthenticationUtil;
import com.brugalibre.util.file.json.JsonUtil;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Sms alert API From MessageBird
 */
public class ClickSendAlertSendServiceImpl implements AlertSendService {

   private static final Logger LOG = LoggerFactory.getLogger(ClickSendAlertSendServiceImpl.class);
   private final Client client;
   private final ClickSendAlertSendHelper clickSendAlertSendHelper;

   public ClickSendAlertSendServiceImpl() {
      this(new ClickSendAlertSendHelper());
   }

   public ClickSendAlertSendServiceImpl(ClickSendAlertSendHelper clickSendAlertSendHelper) {
      this.clickSendAlertSendHelper = clickSendAlertSendHelper;
      this.client = ClientBuilder.newClient();
   }

   @Override
   public AlertSendResponse sendAlert(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) throws AlertSendException {
      LOG.info("Sending text {} to {} receivers", alertSendInfos, alertSendInfos.receivers().size());
      ClickSendSmsMessage clickSendMessage = clickSendAlertSendHelper.createClickSendMessage(alertSendConfig, alertSendInfos);
      Entity<String> payload = Entity.json(JsonUtil.createJsonFromObject(clickSendMessage));
      try {
         return sendAlertWithPayload(alertSendConfig.getSmsSendConfig(), payload);
      } catch (Exception e) {
         throw new AlertSendException(e);
      }
   }

   private AlertSendResponse sendAlertWithPayload(SmsSendConfig smsSendConfig, Entity<String> payload) {
      Response response = createAndSendRestRequest(payload, client, smsSendConfig);
      LOG.info("Sending text done, response [{}]", response);
      return CommonAlertSendResponse.of(response);
   }

   private Response createAndSendRestRequest(Entity<String> payload, Client client, SmsSendConfig smsSendConfig) {
      String targetUrl = clickSendAlertSendHelper.getTargetUrl();
      return client.target(targetUrl)
              .request(MediaType.APPLICATION_JSON_TYPE)
              .header("Authorization", "Basic " + AuthenticationUtil.getEncodedUsernameAndPassword(smsSendConfig.getUsername(), smsSendConfig.getApiKeyProvider()))
              .post(payload);
   }
}
