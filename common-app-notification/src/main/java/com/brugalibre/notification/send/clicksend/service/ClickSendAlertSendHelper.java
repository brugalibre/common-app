package com.brugalibre.notification.send.clicksend.service;

import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.clicksend.config.ClickSendConfig;
import com.brugalibre.notification.send.clicksend.model.msg.common.ClickSendMessage;
import com.brugalibre.notification.send.clicksend.model.msg.email.ClickSendEMailMsg;
import com.brugalibre.notification.send.clicksend.model.msg.email.EMailFrom;
import com.brugalibre.notification.send.clicksend.model.msg.email.EMailTo;
import com.brugalibre.notification.send.clicksend.model.msg.sms.ClickSendSmsMessage;
import com.brugalibre.notification.send.clicksend.model.msg.sms.SingleClickSendSmsMsg;
import com.brugalibre.notification.send.common.model.AlertSendInfos;

public class ClickSendAlertSendHelper {
   private final AlertType alertType;
   private final String clickSendV3SmsSendUrl;
   private final String clickSendV3EMailSendUrl;

   /**
    * Creates a new {@link ClickSendAlertSendHelper} with default values for the target url
    *
    * @param alertType the {@link AlertType}
    * @see ClickSendConfig
    */
   public ClickSendAlertSendHelper(AlertType alertType) {
      this(alertType, ClickSendConfig.CLICKSEND_V3_SMS_SEND_URL, ClickSendConfig.CLICKSEND_V3_EMAIL_SEND_URL);
   }

   /**
    * Constructor for testing purpose
    *
    * @param alertType               the {@link AlertType}
    * @param clickSendV3SmsSendUrl   the url for sending sms
    * @param clickSendV3EMailSendUrl the target url for sending emails
    */
   ClickSendAlertSendHelper(AlertType alertType, String clickSendV3SmsSendUrl, String clickSendV3EMailSendUrl) {
      this.alertType = alertType;
      this.clickSendV3SmsSendUrl = clickSendV3SmsSendUrl;
      this.clickSendV3EMailSendUrl = clickSendV3EMailSendUrl;
   }

   public String getTargetUrl() {
      return switch (alertType) {
         case SMS -> clickSendV3SmsSendUrl;
         case EMAIL -> clickSendV3EMailSendUrl;
      };
   }

   public ClickSendMessage createClickSendMessage(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) {
      return switch (alertType) {
         case SMS -> createClickSendSmsMsg(alertSendConfig.getOriginator(), alertSendInfos);
         case EMAIL -> createClickSendEMailMsg(alertSendConfig.getUsername(), alertSendConfig.getOriginatorMailId(), alertSendInfos);
      };
   }

   private static ClickSendSmsMessage createClickSendSmsMsg(String senderId, AlertSendInfos alertSendInfos) {
      ClickSendSmsMessage clickSendSmsMessage = new ClickSendSmsMessage();
      for (String receiver : alertSendInfos.receivers()) {
         SingleClickSendSmsMsg clickSendMsg = new SingleClickSendSmsMsg();
         clickSendMsg.setBody(alertSendInfos.msg());
         clickSendMsg.setFrom(senderId);
         clickSendMsg.setTo(receiver);
         clickSendSmsMessage.addMessages(clickSendMsg);
      }
      return clickSendSmsMessage;
   }

   private static ClickSendEMailMsg createClickSendEMailMsg(String sender, int emailSenderId, AlertSendInfos alertSendInfos) {
      ClickSendEMailMsg clickSendEMailMsg = new ClickSendEMailMsg();
      clickSendEMailMsg.setBody(alertSendInfos.msg());
      clickSendEMailMsg.setFrom(EMailFrom.of(emailSenderId, sender));
      clickSendEMailMsg.setSubject(alertSendInfos.title());
      for (String receiver : alertSendInfos.receivers()) {
         clickSendEMailMsg.addTo(EMailTo.of(receiver));
      }
      return clickSendEMailMsg;
   }

   public AlertType getAlertType() {
      return alertType;
   }
}
