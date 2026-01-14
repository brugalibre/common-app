package com.brugalibre.notification.api.v1.alerttype;

public enum AlertType {
   /**
    * Defines an alert sent by an SMS, send by the ClickSend service
    */
   CLICK_SEND_SMS,

    /**
    * Defines an alert sent by an SMS, send by the MessageBird service
    */
   MESSAGE_BIRD_SMS,

   /**
    * Defines an alert sent by an e-Mail
    */
   EMAIL,
}
