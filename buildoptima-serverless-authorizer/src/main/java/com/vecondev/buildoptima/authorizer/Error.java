package com.vecondev.buildoptima.authorizer;

public enum Error {

  ACCESS_TOKEN_MISSING(4012, Error.UNAUTHORIZED_HTTP_STATUS, "Access Token Missing"),
  INVALID_ACCESS_TOKEN(4013, Error.UNAUTHORIZED_HTTP_STATUS, "Invalid Access Token"),
  ACCESS_TOKEN_EXPIRED(4014, Error.UNAUTHORIZED_HTTP_STATUS, "Expired Access Token");

  public static final String UNAUTHORIZED_HTTP_STATUS = "UNAUTHORIZED";
  private final Integer code;
  private final String httpStatus;
  private final String message;

  Error(Integer code, String httpStatus, String message) {
    this.code = code;
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public String getHttpStatus() {
    return httpStatus;
  }

  public String getMessage() {
    return message;
  }
}
