package com.brugalibre.notification.send.clicksend.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.clicksend.model.msg.common.ClickSendMessage;
import com.brugalibre.notification.send.clicksend.model.response.ClickSendAlertSendResponse;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.notification.util.AuthenticationUtil;
import com.brugalibre.util.file.json.JsonUtil;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Sms alert API From MessageBird
 */
public class ClickSendAlertSendServiceImpl implements AlertSendService {

   private static final Logger LOG = LoggerFactory.getLogger(ClickSendAlertSendServiceImpl.class);
   private final Client client;
   private final ClickSendAlertSendHelper clickSendAlertSendHelper;

   public ClickSendAlertSendServiceImpl(AlertType alertType) {
      this(new ClickSendAlertSendHelper(alertType));
   }

   public ClickSendAlertSendServiceImpl(ClickSendAlertSendHelper clickSendAlertSendHelper) {
      this.clickSendAlertSendHelper = clickSendAlertSendHelper;
      this.client = ClientBuilder.newClient();
   }

   @Override
   public AlertSendResponse sendAlert(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) throws AlertSendException {
      LOG.info("Sending text {} to {} receivers using service {}", alertSendInfos, alertSendInfos.receivers().size(), clickSendAlertSendHelper.getAlertType());
      ClickSendMessage clickSendMessage = clickSendAlertSendHelper.createClickSendMessage(alertSendConfig, alertSendInfos);
      Entity<String> payload = Entity.json(JsonUtil.createJsonFromObject(clickSendMessage));
      try {
         return sendAlertWithPayload(alertSendConfig, payload);
      } catch (Exception e) {
         throw new AlertSendException(e);
      }
   }

   private AlertSendResponse sendAlertWithPayload(AlertSendConfig alertSendConfig, Entity<String> payload) {
      Response response = createAndSendRestRequest(alertSendConfig, payload, client);
      LOG.info("Sending text done, response [{}]", response);
      return ClickSendAlertSendResponse.of(response);
   }

   private Response createAndSendRestRequest(AlertSendConfig alertSendConfig, Entity<String> payload, Client client) {
      String targetUrl = clickSendAlertSendHelper.getTargetUrl();
      return client.target(targetUrl)
              .request(MediaType.APPLICATION_JSON_TYPE)
              .header("Authorization", "Basic " + AuthenticationUtil.getEncodedUsernameAndPassword(alertSendConfig))
              .post(payload);
   }
}
