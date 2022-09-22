package com.vecondev.buildoptima.mailsender;

import static com.vecondev.buildoptima.mailsender.MailConfigProperties.CONFIRMATION_URI;
import static com.vecondev.buildoptima.mailsender.MailConfigProperties.HOST_ADDRESS;
import static com.vecondev.buildoptima.mailsender.MailConfigProperties.VERIFICATION_URI;
import static javax.mail.Message.RecipientType.TO;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender implements RequestHandler<SQSEvent, Void> {

  private static final String CONFIRMATION_MAIL_SUBJECT = "WELCOME TO OUR WEBSITE";
  private static final String VERIFICATION_MAIL_SUBJECT = "CREATE NEW PASSWORD";
  private static final String FIRSTNAME = "{{FIRSTNAME}}";
  private static final String LINK = "{{LINK}}";
  private static final String CONFIRM = "confirm.html";
  private static final String TEXT_HTML = "text/html";

  @Override
  public Void handleRequest(SQSEvent event, Context context) {
    Message message =
        event.getRecords().stream()
            .map(SQSMessage::getBody)
            .map(Message::getObjectFromString)
            .findAny()
            .orElseThrow(IllegalStateException::new);

    String templateAsString =
        FileUtil.getTemplateAsString(AmazonS3Service.getObject(message.getTemplate()));
    templateAsString = prepare(templateAsString, message);
    sendEmail(message, templateAsString);

    return null;
  }

  private void sendEmail(Message message, String template) {
    try {
      MimeMessage mimeMessage = MailConfig.mimeMessage();
      mimeMessage.setSubject(message.getSubject());
      mimeMessage.setRecipient(TO, new InternetAddress(message.getUserEmail()));
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
            message.getTemplate().equals(CONFIRM)
                ? HOST_ADDRESS + CONFIRMATION_URI + message.getToken()
                : HOST_ADDRESS + VERIFICATION_URI + message.getToken());
    message.setSubject(
        message.getTemplate().contains(CONFIRM)
            ? CONFIRMATION_MAIL_SUBJECT
            : VERIFICATION_MAIL_SUBJECT);

    return template;
  }
}
