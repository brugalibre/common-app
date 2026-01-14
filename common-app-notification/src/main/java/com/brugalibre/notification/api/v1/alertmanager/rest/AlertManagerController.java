package com.brugalibre.notification.api.v1.alertmanager.rest;


import com.brugalibre.notification.api.v1.alertmanager.model.alertmanager.AlertMessage;
import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.alertmanager.model.alertmanager.alert.Alert;
import com.brugalibre.notification.api.v1.alertmanager.model.alertmanager.alert.Annotations;
import com.brugalibre.notification.api.v1.alertmanager.model.alertmanager.alert.Labels;
import com.brugalibre.notification.config.AlertSendConfigProvider;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.notification.send.common.model.CommonAlertSendResponse;
import com.brugalibre.notification.send.common.service.BasicAlertSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/api/v1/alertmanger/notification")
@RestController
public class AlertManagerController {

   public static final String ALERT_TITLE = "Alert from Alertmanager!";
   private final String alertManagerReceiver;
   private final BasicAlertSender alertSendService;
   private static final Logger LOG = LoggerFactory.getLogger(AlertManagerController.class);

   @Autowired
   public AlertManagerController(AlertSendConfigProvider alertSendConfigProvider,
                                 @Value("${application.alertmanager.receiver}") String alertManagerReceiver) {
      this.alertSendService = new BasicAlertSender(alertSendConfigProvider);
      this.alertManagerReceiver = alertManagerReceiver;
   }

   @PostMapping(path = "/on-alert")
   public AlertSendResponse sendNotification(@RequestBody AlertMessage alertMessage) {
      LOG.info("Received alert from alertmanager: {}", alertMessage);
      alertMessage.alerts()
              .forEach(alertManagerAlert -> {
                 String msg = buildMessageToSend(alertManagerAlert);
                 AlertSendInfos alertSendInfos = new AlertSendInfos(AlertType.CLICK_SEND_SMS, ALERT_TITLE, msg, List.of(alertManagerReceiver));
                 LOG.info("Send sms for alert: {} to {}", msg, alertManagerReceiver);
                 alertSendService.sendMessage(alertSendInfos);
              });
      return new CommonAlertSendResponse(200, "OK");
   }

   private static String buildMessageToSend(Alert alert) {
      Labels labels = alert.labels();
      Annotations annotations = alert.annotations();
      LocalDateTime startsAt = LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(alert.startsAt()));
      return "Alert=%s\n is %s for instance=%s, started at '%s'. \nSeverity=%s, Summary=%s".formatted(labels.alertname(), alert.status(),
              labels.instance(), startsAt, labels.severity(), annotations.summary());
   }
}
