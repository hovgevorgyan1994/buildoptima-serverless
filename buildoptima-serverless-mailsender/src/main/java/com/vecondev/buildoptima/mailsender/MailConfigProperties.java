package com.vecondev.buildoptima.mailsender;

public class MailConfigProperties {

  public static final String HOST = System.getenv("SMTP_HOST");
  public static final String PORT = System.getenv("SMTP_PORT");
  public static final String USERNAME = System.getenv("SMTP_USERNAME");
  public static final String PASSWORD = System.getenv("SMTP_PASSWORD");
  public static final String FROM = System.getenv("MAIL_FROM");
  public static final String CONFIRMATION_URI = System.getenv("CONFIRMATION_URI");
  public static final String VERIFICATION_URI = System.getenv("VERIFICATION_URI");
  public static final String HOST_ADDRESS = System.getenv("HOST_ADDRESS");

  private MailConfigProperties() {}
}
