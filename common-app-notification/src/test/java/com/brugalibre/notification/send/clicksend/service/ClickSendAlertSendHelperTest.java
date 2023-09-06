package com.brugalibre.notification.send.clicksend.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.clicksend.config.ClickSendConfig;
import com.brugalibre.notification.send.clicksend.model.msg.common.ClickSendMessage;
import com.brugalibre.notification.send.clicksend.model.msg.email.ClickSendEMailMsg;
import com.brugalibre.notification.send.clicksend.model.msg.sms.ClickSendSmsMessage;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClickSendAlertSendHelperTest {

   @Test
   void createSmsClickSendMsg() {

      // Given
      ClickSendAlertSendHelper clickSendAlertSendHelper = new ClickSendAlertSendHelper(AlertType.SMS);
      String title = "title";
      String msg = "test";
      String receiver1 = "receiver1";
      String receiver2 = "receiver2";
      AlertSendInfos alertSendInfos = new AlertSendInfos(title, msg, List.of(receiver1, receiver2));
      AlertSendConfig alertSendConfig = new AlertSendConfig();
      alertSendConfig.setAlertTypes(List.of(AlertType.EMAIL));

      // When
      ClickSendMessage clickSendMsg = clickSendAlertSendHelper.createClickSendMessage(alertSendConfig, alertSendInfos);

      // Then
      assertThat(clickSendMsg).isInstanceOf(ClickSendSmsMessage.class);
      assertThat(((ClickSendSmsMessage) clickSendMsg).getMessages().size()).isEqualTo(2);
   }

   @Test
   void createEMailClickSendMsg() {

      // Given
      ClickSendAlertSendHelper clickSendAlertSendHelper = new ClickSendAlertSendHelper(AlertType.EMAIL);
      String title = "title";
      String msg = "test";
      String receiver = "receiver";
      AlertSendInfos alertSendInfos = new AlertSendInfos(title, msg, List.of(receiver));
      AlertSendConfig alertSendConfig = new AlertSendConfig();
      alertSendConfig.setAlertTypes(List.of(AlertType.SMS));

      // When
      ClickSendMessage clickSendMsg = clickSendAlertSendHelper.createClickSendMessage(alertSendConfig, alertSendInfos);

      // Then
      assertThat(clickSendMsg).isInstanceOf(ClickSendEMailMsg.class);
   }

   @Test
   void getTargetUrlForEMail() {

      // Given
      ClickSendAlertSendHelper clickSendAlertSendHelper = new ClickSendAlertSendHelper(AlertType.EMAIL);

      // When
      String actualTargetUrl = clickSendAlertSendHelper.getTargetUrl();

      // Then
      assertThat(actualTargetUrl).isEqualTo(ClickSendConfig.CLICKSEND_V3_EMAIL_SEND_URL);
   }

   @Test
   void getTargetUrlForSms() {

      // Given
      ClickSendAlertSendHelper clickSendAlertSendHelper = new ClickSendAlertSendHelper(AlertType.SMS);

      // When
      String actualTargetUrl = clickSendAlertSendHelper.getTargetUrl();

      // Then
      assertThat(actualTargetUrl).isEqualTo(ClickSendConfig.CLICKSEND_V3_SMS_SEND_URL);
   }

   @Test
   void getAlertType() {
      // Given
      ClickSendAlertSendHelper clickSendAlertSendHelper = new ClickSendAlertSendHelper(AlertType.SMS);

      // When
      AlertType actualAlertType = clickSendAlertSendHelper.getAlertType();

      // Then
      assertThat(actualAlertType).isEqualTo(AlertType.SMS);
   }
}