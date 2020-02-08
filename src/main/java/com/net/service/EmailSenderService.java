package com.net.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailSenderService {
	@Autowired
	@Qualifier("emailSender")
	private JavaMailSenderImpl emailSender;

	@Value("${mail.sendAddress}")
	private String sendAddress;

	//	@Async
	public void sendEmailUsingBcc(List<String> bccRecipients, String subject, String htmlBody) {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
		try {
			messageHelper.setBcc(bccRecipients.toArray(new String[]{}));
			sendEmail(subject, htmlBody, mimeMessage);
		} catch (MessagingException ex) {
			System.out.println(ex);
		}
	}

	//	@Async
	public void sendEmailUsingTo(List<String> toRecipients, String subject, String htmlBody) {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
		try {
			messageHelper.setTo(toRecipients.toArray(new String[]{}));
			sendEmail(subject, htmlBody, mimeMessage);
		} catch (MessagingException ex) {
			System.out.println(ex);
		}
	}

	private void sendEmail(String subject, String htmlBody, MimeMessage mimeMessage) throws MessagingException {
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
		messageHelper.setSubject(subject);
		messageHelper.setText(htmlBody, true);
		messageHelper.setFrom(sendAddress);
		emailSender.send(mimeMessage);
	}
}
