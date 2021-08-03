package com.balance.service;

import com.balance.model.User;
import com.balance.model.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class EmailService {

   private static final Logger log = LoggerFactory.getLogger(EmailService.class);
   private static final String LOCATION_KEY = "location";
   private static final String SIGN_KEY = "sign";
   private static final String NAME_KEY = "name";
   private static final String ACCOUNT_ACTIVATION_TEMPLATE = "account-activation";
   private static final String ACTIVATION_LINK_KEY = "activationLink";
   private static final String API_ACTIVATE_TOKEN = "/api/activate?activationToken=";

   @Value("${server.baseUrl}")
   private String baseUrl;
   @Value("${mail.smtp.sender.address}")
   private String senderAddress;
   @Value("${mail.sign}")
   private String mailSign;
   @Value("${mail.location}")
   private String mailLocation;

   private final JavaMailSender mailSender;
   private final SpringTemplateEngine templateEngine;

   public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
      this.mailSender = mailSender;
      this.templateEngine = templateEngine;
   }

   public void sendVerificationTokenEmail(VerificationToken verificationToken) {
      try {
         MimeMessage activationEmail = createActivationEmail(verificationToken);
         log.info("Sending activation email to {}", Arrays.toString(activationEmail.getAllRecipients()));
         mailSender.send(activationEmail);
      } catch (MessagingException exception) {
         log.error("Failed to create a message", exception);
      }
   }

   private MimeMessage createActivationEmail(VerificationToken verificationToken) throws MessagingException {
      String email = verificationToken.getUser().getUsername();
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
      Map<String, Object> mailProperties = getActivationMailProperties(verificationToken);
      String html = templateEngine.process(ACCOUNT_ACTIVATION_TEMPLATE, new Context(Locale.getDefault(), mailProperties));

      mimeMessageHelper.setSubject("User Account Activation");
      mimeMessageHelper.setText(html, true);
      mimeMessageHelper.setFrom(senderAddress);
      mimeMessageHelper.setTo(email);

      return mimeMessage;
   }

   private Map<String, Object> getActivationMailProperties(VerificationToken verificationToken) {
      User user = verificationToken.getUser();
      Map<String, Object> mailProperties = new HashMap<>();
      mailProperties.put(NAME_KEY, user.getFirstName());
      mailProperties.put(SIGN_KEY, mailSign);
      mailProperties.put(LOCATION_KEY, mailLocation);
      mailProperties.put(ACTIVATION_LINK_KEY, baseUrl + API_ACTIVATE_TOKEN + verificationToken.getToken());
      return mailProperties;
   }
}
