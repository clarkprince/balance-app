package com.balance.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Configuration
public class MailConfig {
   private static final String MAIL_TRANSPORT_PROTOCOL = "mail.transport.protocol";
   private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
   private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
   private static final String MAIL_DEBUG = "mail.debug";
   private static final String TEMPLATES_PATH = "classpath:/templates/";
   private static final String HTML_SUFFIX = ".html";

   @Value("${mail.transport.protocol}")
   private String transportProtocol;
   @Value("${mail.smtp.host}")
   private String smtpHost;
   @Value("${mail.smtp.port}")
   private int smtpPort;
   @Value("${mail.smtp.username}")
   private String smtpUsername;
   @Value("${mail.smtp.password}")
   private String smtpPassword;
   @Value("${mail.smtp.auth}")
   private boolean smtpAuth;
   @Value("${mail.smtp.starttls.enable}")
   private boolean smtpTLSEnabled;
   @Value("${mail.smtp.debug}")
   private boolean smtpDebug;

   @Bean
   public JavaMailSender getJavaMailSender() {
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
      mailSender.setHost(smtpHost);
      mailSender.setPort(smtpPort);

      mailSender.setUsername(smtpUsername);
      mailSender.setPassword(smtpPassword);

      Properties props = mailSender.getJavaMailProperties();
      props.put(MAIL_TRANSPORT_PROTOCOL, transportProtocol);
      props.put(MAIL_SMTP_AUTH, smtpAuth);
      props.put(MAIL_SMTP_STARTTLS_ENABLE, smtpTLSEnabled);
      props.put(MAIL_DEBUG, smtpDebug);

      return mailSender;
   }

   @Bean
   public SpringTemplateEngine springTemplateEngine(SpringResourceTemplateResolver htmlTemplateResolver) {
      SpringTemplateEngine templateEngine = new SpringTemplateEngine();
      templateEngine.addTemplateResolver(htmlTemplateResolver);
      templateEngine.setEnableSpringELCompiler(true);
      return templateEngine;
   }

   @Bean
   public SpringResourceTemplateResolver htmlTemplateResolver() {
      SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
      emailTemplateResolver.setPrefix(TEMPLATES_PATH);
      emailTemplateResolver.setSuffix(HTML_SUFFIX);
      emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
      emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
      return emailTemplateResolver;
   }
}
