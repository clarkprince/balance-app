package com.balance.service;

import com.balance.model.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@Component
public class EmailService {

   private static final Logger log = LoggerFactory.getLogger(EmailService.class);

   @Value("${server.baseUrl}")
   private String baseUrl;

   private final JavaMailSender javaMailSender;


   public EmailService(JavaMailSender javaMailSender) {
      this.javaMailSender = javaMailSender;
   }


   public void sendVerificationTokenEmail(VerificationToken verificationToken) {
      try {
         MimeMessage activationEmail = createActivationEmail(verificationToken);
         log.info("Sending activation email to {}", Arrays.toString(activationEmail.getAllRecipients()));
         javaMailSender.send(activationEmail);
      } catch (MessagingException exception) {
         log.error("Failed to create a message", exception);
      }
   }

   private MimeMessage createActivationEmail(VerificationToken verificationToken) throws MessagingException {
      String email = verificationToken.getUser().getEmail();
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
      mimeMessageHelper.setSubject("User Account Activation");
      mimeMessageHelper.setText(baseUrl + "/api/activate?activationToken=" + verificationToken.getToken());
      mimeMessageHelper.setFrom("noreply@mail.com");
      mimeMessageHelper.setTo(email);
      return mimeMessage;
   }
}
