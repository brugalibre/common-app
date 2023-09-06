package com.brugalibre.notification.api.v1.service;

/**
 * A {@link AlertSendException} is thrown whenever the sending of an alert failed
 */
public class AlertSendException extends Exception {
   public AlertSendException(Exception e) {
      super(e);
   }
}
