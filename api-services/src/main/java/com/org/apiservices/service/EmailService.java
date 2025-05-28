package com.org.apiservices.service;

import com.org.apiservices.dto.CommonResponse;

public interface EmailService {

	CommonResponse sendEmail(String to, String subject, String text);

}
