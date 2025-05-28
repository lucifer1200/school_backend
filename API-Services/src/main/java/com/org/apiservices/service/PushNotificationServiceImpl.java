package com.org.apiservices.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.CommonResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PushNotificationServiceImpl implements PushNotificationService {

	@Override
	public CommonResponse sendPushNotification(String token, String title, String body) {
		CommonResponse lresponse = new CommonResponse();
		Notification notification = Notification.builder().setTitle(title).setBody(body).build();

		Message message = Message.builder().setToken(token).setNotification(notification).build();

		try {
			String response = FirebaseMessaging.getInstance().send(message);
			System.out.println("Successfully sent message: " + response);
			lresponse.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
			lresponse.setMessage("Successfully sent message: " + response);
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