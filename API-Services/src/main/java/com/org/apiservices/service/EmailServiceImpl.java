package com.org.apiservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.CommonResponse;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public CommonResponse sendEmail(String to, String subject, String text) {
		CommonResponse lresponse = new CommonResponse();
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject(subject);
			message.setText(text);
			message.setFrom("your-email@example.com");

			mailSender.send(message);

			lresponse.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
			lresponse.setMessage("Successfully sent message: ");
			lresponse.setStatusDesc(MessageConstants.SUCCESS);
		} catch (Exception e) {
			lresponse.setStatusCode(MessageConstants.ERROR_STATUSCODE);
			lresponse.setMessage(e.toString());
			lresponse.setStatusDesc(MessageConstants.FAILURE);
			e.printStackTrace();
		}

		return lresponse;
	}

}