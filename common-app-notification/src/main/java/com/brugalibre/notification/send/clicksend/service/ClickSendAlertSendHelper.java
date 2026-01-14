package com.brugalibre.notification.send.clicksend.service;

import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.send.clicksend.model.ClickSendSmsMessage;
import com.brugalibre.notification.send.clicksend.model.SingleClickSendSmsMsg;
import com.brugalibre.notification.send.common.model.AlertSendInfos;

import java.util.ArrayList;
import java.util.List;

public class ClickSendAlertSendHelper {

   public static final String CLICKSEND_V3_SMS_SEND_URL = "https://rest.clicksend.com/v3/sms/send";
   private final String clickSendV3SmsSendUrl;

   /**
    * Creates a new {@link ClickSendAlertSendHelper} with default values for the target url
    */
   public ClickSendAlertSendHelper() {
      this(CLICKSEND_V3_SMS_SEND_URL);
   }

   /**
    * Constructor for testing purpose
    *
    * @param clickSendV3SmsSendUrl the url for sending sms
    */
   ClickSendAlertSendHelper(String clickSendV3SmsSendUrl) {
      this.clickSendV3SmsSendUrl = clickSendV3SmsSendUrl;
   }

   public String getTargetUrl() {
      return clickSendV3SmsSendUrl;
   }

   public ClickSendSmsMessage createClickSendMessage(AlertSendConfig alertSendConfig, AlertSendInfos alertSendInfos) {
      List<SingleClickSendSmsMsg> singleClickSendSmsMsgList = new ArrayList<>();
      for (String receiver : alertSendInfos.receivers()) {
         SingleClickSendSmsMsg clickSendMsg = new SingleClickSendSmsMsg();
         clickSendMsg.setBody(alertSendInfos.msg());
         clickSendMsg.setFrom(alertSendConfig.getSmsSendConfig().getOriginator());
         clickSendMsg.setTo(receiver);
         singleClickSendSmsMsgList.add(clickSendMsg);
      }
      return new ClickSendSmsMessage(singleClickSendSmsMsgList);
   }
}
