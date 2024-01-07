package com.app.service;

import java.io.File;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.app.dto.EmailDetails;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String senderMail;

	@Override
	@Async
	public void sendEmailAlert(EmailDetails emailDetails) {
		try {
			SimpleMailMessage message=new SimpleMailMessage();
			message.setFrom(senderMail);
			message.setTo(emailDetails.getReceipient());
			message.setSubject(emailDetails.getSubject());
			message.setText(emailDetails.getMessageBody());
			
			javaMailSender.send(message);
			log.info("Mail sent successfully");
		} catch (MailException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Async
	public void sendEmailWithAttachment(EmailDetails emailDetails) {
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
			mimeMessageHelper.setFrom(senderMail);
			mimeMessageHelper.setTo(emailDetails.getReceipient());
			mimeMessageHelper.setText(emailDetails.getMessageBody());
			mimeMessageHelper.setSubject(emailDetails.getSubject());
			
			FileSystemResource file=new FileSystemResource(new File(emailDetails.getAttachments()));
			mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
			
			javaMailSender.send(mimeMessage);
			
			log.info(file.getFilename()+" has been sent to user with email "+emailDetails.getReceipient());
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
