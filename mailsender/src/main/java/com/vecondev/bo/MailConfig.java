package com.vecondev.bo;

import static com.vecondev.bo.MailConfigProperties.FROM;
import static com.vecondev.bo.MailConfigProperties.HOST;
import static com.vecondev.bo.MailConfigProperties.PASSWORD;
import static com.vecondev.bo.MailConfigProperties.PORT;
import static com.vecondev.bo.MailConfigProperties.USERNAME;

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
    Properties properties = new Properties();
    properties.put(SMTP_AUTH, TRUE);
    properties.put(SMTP_STARTTLS, TRUE);
    properties.put(SMTP_HOST, HOST);
    properties.put(SMTP_PORT, PORT);
    properties.put(SMTP_SSL_TRUST, HOST);
    properties.put(SMTP_PROTOCOL, TLS);

    Session session =
        Session.getInstance(
            properties,
            new Authenticator() {
              @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
              }
            });

    MimeMessage message = new MimeMessage(session);
    try {
      message.setFrom(new InternetAddress(FROM));
      return message;
    } catch (MessagingException e) {
      throw new IllegalStateException(e);
    }
  }
}
