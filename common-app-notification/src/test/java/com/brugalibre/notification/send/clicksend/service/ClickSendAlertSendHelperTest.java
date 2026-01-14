package com.brugalibre.notification.send.clicksend.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.clicksend.model.ClickSendSmsMessage;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.brugalibre.notification.send.clicksend.service.ClickSendAlertSendHelper.CLICKSEND_V3_SMS_SEND_URL;
import static org.assertj.core.api.Assertions.assertThat;

class ClickSendAlertSendHelperTest {

   @Test
   void createSmsClickSendMsg() {

      // Given
      ClickSendAlertSendHelper clickSendAlertSendHelper = new ClickSendAlertSendHelper();
      String title = "title";
      String msg = "test";
      String receiver1 = "receiver1";
      String receiver2 = "receiver2";
      AlertSendInfos alertSendInfos = new AlertSendInfos(AlertType.CLICK_SEND_SMS, title, msg, List.of(receiver1, receiver2));
      AlertSendConfig alertSendConfig = new AlertSendConfig();

      // When
      ClickSendSmsMessage clickSendMsg = clickSendAlertSendHelper.createClickSendMessage(alertSendConfig, alertSendInfos);

      // Then
      assertThat(clickSendMsg).isInstanceOf(ClickSendSmsMessage.class);
      assertThat(clickSendMsg.messages().size()).isEqualTo(2);
   }

   @Test
   void getTargetUrlForSms() {

      // Given
      ClickSendAlertSendHelper clickSendAlertSendHelper = new ClickSendAlertSendHelper();

      // When
      String actualTargetUrl = clickSendAlertSendHelper.getTargetUrl();

      // Then
      assertThat(actualTargetUrl).isEqualTo(CLICKSEND_V3_SMS_SEND_URL);
   }
}