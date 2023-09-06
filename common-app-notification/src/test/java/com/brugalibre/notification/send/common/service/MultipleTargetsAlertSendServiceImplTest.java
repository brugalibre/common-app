package com.brugalibre.notification.send.common.service;

import com.brugalibre.notification.api.v1.service.AlertSendException;
import com.brugalibre.notification.api.v1.model.AlertSendResponse;
import com.brugalibre.notification.api.v1.service.AlertSendService;
import com.brugalibre.notification.config.AlertSendConfig;
import com.brugalibre.notification.api.v1.alerttype.AlertType;
import com.brugalibre.notification.send.common.model.AlertSendInfos;
import com.brugalibre.notification.send.common.model.MultipleAlertSendResponses;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class MultipleTargetsAlertSendServiceImplTest {

   @Test
   void sendAlertWithMultipleAlertSendersDifferentStatus() throws AlertSendException {
      // Given
      String responseEntity = "responseEntity";
      List<String> receivers = List.of("receiver");
      AlertSendService alertSendService = mock(AlertSendService.class);
      List<String> expectedResponseEntity = List.of(responseEntity, responseEntity);

      AtomicInteger status = new AtomicInteger(200);
      int expectedStatus = 500;
      when(alertSendService.sendAlert(any(), any())).thenReturn(new AlertSendResponse() {
         @Override
         public int getStatus() {
            int currentStatus = status.get();
            status.set(expectedStatus);
            return currentStatus;
         }

         @Override
         public Object getResponseEntity() {
            return responseEntity;
         }
      });
      MultipleTargetsAlertSendServiceImpl multipleTargetsAlertSendService = new MultipleTargetsAlertSendServiceImpl(alertType -> alertSendService);
      String msg = "test";
      AlertSendConfig alertSendConfig = new AlertSendConfig();
      alertSendConfig.setAlertTypes(List.of(AlertType.EMAIL, AlertType.SMS));
      AlertSendInfos alertSendInfos = new AlertSendInfos(msg, receivers);

      // When
      AlertSendResponse alertSendResponse = multipleTargetsAlertSendService.sendAlert(alertSendConfig, alertSendInfos);

      // Then
      verify(alertSendService, times(2)).sendAlert(eq(alertSendConfig), eq(alertSendInfos));
      assertThat(alertSendResponse).isInstanceOf(MultipleAlertSendResponses.class);
      assertThat(alertSendResponse.getStatus()).isEqualTo(expectedStatus);
      assertThat(alertSendResponse.getResponseEntity()).isEqualTo(expectedResponseEntity);
      assertThat(((MultipleAlertSendResponses) alertSendResponse).getAlertSendResponses().size()).isEqualTo(2);
   }

   @Test
   void sendAlertWithMultipleAlertSendersSameStatus() throws AlertSendException {
      // Given
      String responseEntity = "responseEntity";
      List<String> receivers = List.of("receiver");
      AlertSendService alertSendService = mock(AlertSendService.class);
      List<String> expectedResponseEntity = List.of(responseEntity, responseEntity);

      int expectedStatus = 200;
      when(alertSendService.sendAlert(any(), any())).thenReturn(new AlertSendResponse() {
         @Override
         public int getStatus() {
            return 200;
         }

         @Override
         public Object getResponseEntity() {
            return responseEntity;
         }
      });
      MultipleTargetsAlertSendServiceImpl multipleTargetsAlertSendService = new MultipleTargetsAlertSendServiceImpl(alertType -> alertSendService);
      String msg = "test";
      AlertSendConfig alertSendConfig = new AlertSendConfig();
      alertSendConfig.setAlertTypes(List.of(AlertType.EMAIL, AlertType.SMS));
      AlertSendInfos alertSendInfos = new AlertSendInfos(msg, receivers);

      // When
      AlertSendResponse alertSendResponse = multipleTargetsAlertSendService.sendAlert(alertSendConfig, alertSendInfos);

      // Then
      verify(alertSendService, times(2)).sendAlert(eq(alertSendConfig), eq(alertSendInfos));
      assertThat(alertSendResponse.getStatus()).isEqualTo(expectedStatus);
      assertThat(alertSendResponse.getResponseEntity()).isEqualTo(expectedResponseEntity);
   }
}