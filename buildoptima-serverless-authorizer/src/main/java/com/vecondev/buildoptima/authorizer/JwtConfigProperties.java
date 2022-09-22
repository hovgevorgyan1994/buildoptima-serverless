package com.vecondev.buildoptima.authorizer;

public class JwtConfigProperties {

  public static final String SIGNATURE_ALGORITHM_ENV_VARIABLE = "SIGNATURE_ALGORITHM";
  public static final String AUTHORIZATION_HEADER_PREFIX_ENV_VARIABLE =
      "AUTHORIZATION_HEADER_PREFIX";
  private static String signatureAlgorithm;
  private static String authorizationHeaderPrefix;

  static {
    signatureAlgorithm = System.getenv(SIGNATURE_ALGORITHM_ENV_VARIABLE);
    authorizationHeaderPrefix = System.getenv(AUTHORIZATION_HEADER_PREFIX_ENV_VARIABLE);
  }

  private JwtConfigProperties() {}

  public static String getSignatureAlgorithm() {
    return signatureAlgorithm;
  }

  public static String getAuthorizationHeaderPrefix() {
    return authorizationHeaderPrefix;
  }
}