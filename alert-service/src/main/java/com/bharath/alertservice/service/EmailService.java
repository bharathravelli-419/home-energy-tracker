package com.bharath.alertservice.service;

import com.bharath.alertservice.entity.Alert;
import com.bharath.alertservice.repostiory.AlertRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final AlertRepository alertRepository;

    public void sendEmail(String to, String subject, String body, Long userId) throws MessagingException {
        log.info("Sending email to : {}",to);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setFrom("noreply@bharath.com");
//        message.setSubject(subject);
        MimeMessage message = mailSender.createMimeMessage();

        // The true flag indicates you want to create a multipart message (useful if adding HTML or text content)
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, false); // 'false' implies standard plaintext. Change to 'true' for HTML layout strings.
        helper.setFrom("no-reply@energytracker.com"); // Ensure a valid sending envelope address is present

        // Send the fully constructed message payload
//        mailSender.send(message);

        try{
            mailSender.send(message);

            final Alert alert = Alert.builder()
                    .sent(true)
                    .createdAt(LocalDateTime.now())
                    .userId(userId)
                    .build();

            alertRepository.saveAndFlush(alert);
        }
        catch (Exception e){
            log.info("Failed to send email to: {}", to,e);
            final Alert alert = Alert.builder()
                    .sent(false)
                    .createdAt(LocalDateTime.now())
                    .userId(userId)
                    .build();

            alertRepository.saveAndFlush(alert);

            return;
        }

        log.info("Sent email to: {} with user id: {}",to, userId);
    }

}
