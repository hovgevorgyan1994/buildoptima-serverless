package com.vecondev.buildoptima.mailsender;

import static com.vecondev.buildoptima.mailsender.MailConfigProperties.FROM;
import static com.vecondev.buildoptima.mailsender.MailConfigProperties.HOST;
import static com.vecondev.buildoptima.mailsender.MailConfigProperties.PASSWORD;
import static com.vecondev.buildoptima.mailsender.MailConfigProperties.PORT;
import static com.vecondev.buildoptima.mailsender.MailConfigProperties.USERNAME;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailConfig {

  private static final String SMTP_AUTH = "mail.smtp.auth";
  private static final String SMTP_STARTTLS = "mail.smtp.starttls.enable";
  private static final String SMTP_HOST = "mail.smtp.host";
  private static final String SMTP_PORT = "mail.smtp.port";
  private static final String SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
  private static final String SMTP_PROTOCOL = "mail.smtp.ssl.protocols";
  private static final String TRUE = "true";
  private static final String TLS = "TLSv1.2";

  private MailConfig() {}

  public static MimeMessage mimeMessage() {
    MimeMessage message = new MimeMessage(createSession());
    try {
      message.setFrom(new InternetAddress(FROM));
      return message;
    } catch (MessagingException e) {
      throw new IllegalStateException(e);
    }
  }

  private static Session createSession() {
    return Session.getInstance(
        createProperties(),
        new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(USERNAME, PASSWORD);
          }
        });
  }

  private static Properties createProperties() {
    Properties properties = new Properties();
    properties.put(SMTP_AUTH, TRUE);
    properties.put(SMTP_STARTTLS, TRUE);
    properties.put(SMTP_HOST, HOST);
    properties.put(SMTP_PORT, PORT);
    properties.put(SMTP_SSL_TRUST, HOST);
    properties.put(SMTP_PROTOCOL, TLS);
    return properties;
  }
}
