package com.vecondev.buildoptima.authorizer;

public class ConfigProperties {

  private static final String SIGNATURE_ALGORITHM_ENV_VARIABLE = "SIGNATURE_ALGORITHM";
  private static final String AUTHORIZATION_HEADER_PREFIX_ENV_VARIABLE =
      "AUTHORIZATION_HEADER_PREFIX";

  public static final String SIGNATURE_ALGORITHM = System.getenv(SIGNATURE_ALGORITHM_ENV_VARIABLE);
  public static final String AUTHORIZATION_HEADER_PREFIX =
      System.getenv(AUTHORIZATION_HEADER_PREFIX_ENV_VARIABLE);

  private ConfigProperties() {}
}