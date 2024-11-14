package com.zarnicki.giftrandom;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.exceptions.MailerSendException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  public void sendEmailWithGiftRecipient() throws MailerSendException {
    generatePairs();
  }

  public void sendEmailv2(Person from, Person to) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(from.email);
    message.setSubject("Maszyna losująca wybrała Ci osobę!");
    message.setText("Hejka naklejka, masz zrobić prezent dla: " + to.getName());
    mailSender.send(message);
  }


  public void sendEmail(Person from, Person to) throws MailerSendException {
    Email email = new Email();
    email.subject = "Maszyna losująca wybrała Ci osobę!";
    email.text = "Hejka naklejka, masz zrobić prezent dla: " + to.getName();
    email.addRecipient(from.getEmail(), from.getEmail());
    email.setFrom("Pomocnik mikołaja", "");
    MailerSend ms = new MailerSend();
    ms.setToken("");
    MailerSendResponse response = ms.emails().send(email);
    System.out.println(
        "Email sent to: " + from.getName() + " with code: " + response.responseStatusCode);
  }

  public void generatePairs() throws MailerSendException {
    List<Person> people = new ArrayList<>();
    people.add(new Person("name", "email"));
    people.add(new Person("name", "email"));

    List<Person> assignedPairs = assignUniquePairs(people);

    for (int i = 0; i < people.size(); i++) {
      //System.out.println(people.get(i).getName() + " -> " + assignedPairs.get(i).getName());
      sendEmailv2(people.get(i), assignedPairs.get(i));
    }
  }

  public List<Person> assignUniquePairs(List<Person> people) {
    List<Person> shuffledList = new ArrayList<>(people);
    do {
      Collections.shuffle(shuffledList);
    } while (hasSelfAssignment(people, shuffledList));

    return shuffledList;
  }

  private boolean hasSelfAssignment(List<Person> originalList, List<Person> shuffledList) {
    for (int i = 0; i < originalList.size(); i++) {
      if (originalList.get(i).equals(shuffledList.get(i))) {
        return true;
      }
    }
    return false;
  }
}
