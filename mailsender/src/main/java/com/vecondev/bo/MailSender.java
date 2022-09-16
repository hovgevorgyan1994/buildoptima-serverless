package com.vecondev.bo;

import static com.vecondev.bo.MailConfigProperties.CONFIRMATION_URI;
import static com.vecondev.bo.MailConfigProperties.HOST_ADDRESS;
import static com.vecondev.bo.MailConfigProperties.VERIFICATION_URI;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.amazonaws.services.s3.model.S3Object;
import javax.mail.Message.RecipientType;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender implements RequestHandler<SQSEvent, Void> {

  private static final String CONFIRMATION_MAIL_SUBJECT = "WELCOME TO OUR WEBSITE";
  private static final String VERIFICATION_MAIL_SUBJECT = "CREATE NEW PASSWORD";
  private static final String FIRSTNAME = "{{FIRSTNAME}}";
  private static final String LINK = "{{LINK}}";
  private static final String CONFIRM = "confirm";
  private static final String TEXT_HTML = "text/html";

  @Override
  public Void handleRequest(SQSEvent event, Context context) {
    Message message =
        event.getRecords().stream()
            .map(SQSMessage::getBody)
            .map(Message::getObjectFromString)
            .findAny()
            .orElseThrow();

    S3Object object = AmazonS3Service.getObject(message.getTemplate());
    String template = FileUtil.getTemplateAsString(object);
    template = prepare(template, message);
    sendEmail(message, template);
    return null;
  }

  private void sendEmail(Message message, String template) {
    try {
      MimeMessage mimeMessage = MailConfig.mimeMessage();
      mimeMessage.setSubject(message.getSubject());
      mimeMessage.setRecipient(RecipientType.TO, new InternetAddress(message.getUserEmail()));
      mimeMessage.setContent(template, TEXT_HTML);
      Transport.send(mimeMessage);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private String prepare(String template, Message message) {
    template = template.replace(FIRSTNAME, message.getUserFirstName());
    template =
        template.replace(
            LINK,
            message.getTemplate().contains(CONFIRM)
                ? HOST_ADDRESS + CONFIRMATION_URI + message.getToken()
                : HOST_ADDRESS + VERIFICATION_URI + message.getToken());
    message.setSubject(
        message.getTemplate().contains(CONFIRM)
            ? CONFIRMATION_MAIL_SUBJECT
            : VERIFICATION_MAIL_SUBJECT);
    return template;
  }
}
