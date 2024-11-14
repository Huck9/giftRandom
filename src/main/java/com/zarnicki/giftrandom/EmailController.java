package com.zarnicki.giftrandom;

import com.mailersend.sdk.exceptions.MailerSendException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

  @Autowired
  private EmailService emailService;

  @PostMapping("/send")
  public void sendEmail() throws MailerSendException {
    emailService.sendEmailWithGiftRecipient();
    System.out.println("Email sent");
  }
}
