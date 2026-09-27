package com.bharath.alertservice.service;

import com.bharath.kafka.event.AlertingEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AlertService {

    private final EmailService emailService;

    @KafkaListener(topics = "energy-alerts", groupId = "alert-service")
    public void energyUsageAlertEvent(AlertingEvent alertingEvent) throws MessagingException {
        log.info("Received Alerting Event: {}", alertingEvent);

        final String subject = "Energy usage Alert fro User :"+ alertingEvent.userId();

        final String message = "Alert: "+ alertingEvent.message()+
                "\n Threshold: "+ alertingEvent.threshold()+
                "\n Energy Consumed: "+ alertingEvent.energyConsumed();
        emailService.sendEmail(alertingEvent.email(),subject, message, alertingEvent.userId());

    }
}
