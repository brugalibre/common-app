package com.brugalibre.notification.api.v1.rest;


import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.config.AlertSendConfigProvider;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.notification.send.common.service.BasicAlertSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/notification")
@RestController
public class AlertSendController {

   private final BasicAlertSender alertSendService;

   @Autowired
   public AlertSendController(AlertSendConfigProvider alertSendConfigProvider) {
      this.alertSendService = new BasicAlertSender(alertSendConfigProvider);
   }

   @PostMapping(path = "/sent-notification")
   public AlertSendResponse sendNotification(@RequestBody AlertSendInfos alertSendInfos) {
      return alertSendService.sendMessage(alertSendInfos);
   }
}
