package com.vecondev.bo;

public class MailConfigProperties {

  public static final String HOST;
  public static final String PORT;
  public static final String USERNAME;
  public static final String PASSWORD;
  public static final String FROM;
  public static final String CONFIRMATION_URI;
  public static final String VERIFICATION_URI;
  public static final String HOST_ADDRESS;

  private MailConfigProperties() {}

  static {
    HOST = System.getenv("SMTP_HOST");
    PORT = System.getenv("SMTP_PORT");
    USERNAME = System.getenv("SMTP_USERNAME");
    PASSWORD = System.getenv("SMTP_PASSWORD");
    FROM = System.getenv("MAIL_FROM");
    CONFIRMATION_URI = System.getenv("CONFIRMATION_URI");
    VERIFICATION_URI = System.getenv("VERIFICATION_URI");
    HOST_ADDRESS = System.getenv("HOST_ADDRESS");
  }
}
