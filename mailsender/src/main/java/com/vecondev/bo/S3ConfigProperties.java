package com.vecondev.bo;

public class S3ConfigProperties {

  public static final String BUCKET;
  public static final String ACCESS;
  public static final String SECRET;

  private S3ConfigProperties() {}

  static {
    BUCKET = System.getenv("S3_BUCKET_NAME");
    ACCESS = System.getenv("AWS_ACCESS");
    SECRET = System.getenv("AWS_SECRET");
  }
}
