package com.brugalibre.notification.send.clicksend.service;

import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.test.http.DummyHttpServerTestCaseBuilder;
import com.brugalibre.util.file.yml.YamlService;
import org.junit.jupiter.api.Test;
import org.mockserver.model.Header;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ClickSendAlertSendServiceImplTest {
   private static final String ALERT_TEST_AQUABASILEA_ALERT_NOTIFICATION_YML = "alert/test-alert-notification.yml";
   private static final String HOST = "http://127.0.0.1";
   private static final String POST = "POST";

   @Test
   void sendAlert() throws AlertSendException {
      // Given
      int port = 8181;
      String path = "/v3/send/sms";
      String msg = "test-message";
      AlertSendInfos alertSendInfos = getAlertSendInfos(msg);
      AlertSendConfig alertSendConfig = new YamlService().readYaml(ALERT_TEST_AQUABASILEA_ALERT_NOTIFICATION_YML, AlertSendConfig.class);
      alertSendConfig.setApiKeyProvider("password"::toCharArray);

      String response = "OK";
      String authValue = "Basic bnVsbDpwYXNzd29yZA==";// change if user/password from above is changed
      DummyHttpServerTestCaseBuilder serverTestCaseBuilder = new DummyHttpServerTestCaseBuilder(port)
              .withHost(HOST)
              .withRequestResponse(path)
              .withMethod(POST)
              .withResponseBody(response)
              .withHeader(new Header("Authorization", authValue))
              .buildRequestResponse()
              .build();
      ClickSendAlertSendHelper clickSendHelper = new ClickSendAlertSendHelper(AlertType.SMS, HOST + ":" + port + path, null);
      ClickSendAlertSendServiceImpl clickSendAlertSendServiceImpl = new ClickSendAlertSendServiceImpl(clickSendHelper);

      // When
      AlertSendResponse actualResponse = clickSendAlertSendServiceImpl.sendAlert(alertSendConfig, alertSendInfos);

      // Then
      assertThat(actualResponse.getStatus(), is(200));
      assertThat(actualResponse.getResponseEntity().toString(), is(response));
   }

   private static AlertSendInfos getAlertSendInfos(String msg) {
      List<String> receiver = List.of("0791234567");
      return new AlertSendInfos(msg, receiver);
   }
}