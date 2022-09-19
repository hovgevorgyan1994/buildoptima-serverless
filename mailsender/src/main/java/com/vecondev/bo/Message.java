package com.vecondev.bo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Message {
  private String template;
  private String subject;
  private String token;
  private String userEmail;
  private String userFirstName;

  public Message() {}

  public Message(
      String template, String subject, String token, String email, String userFirstName) {
    this.template = template;
    this.subject = subject;
    this.token = token;
    this.userEmail = email;
    this.userFirstName = userFirstName;
  }

  public static Message getObjectFromString(String object) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    try {
      return mapper.readValue(object, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public String getUserFirstName() {
    return userFirstName;
  }

  public void setUserFirstName(String userFirstName) {
    this.userFirstName = userFirstName;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
}
