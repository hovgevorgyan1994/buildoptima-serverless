package com.vecondev.buildoptima.mailsender;

public class S3ConfigProperties {

  public static final String BUCKET = System.getenv("S3_BUCKET_NAME");
  public static final String ACCESS = System.getenv("AWS_ACCESS");
  public static final String SECRET = System.getenv("AWS_SECRET");

  private S3ConfigProperties() {}
}
