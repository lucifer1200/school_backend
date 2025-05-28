package com.org.apiservices.service;

import com.org.apiservices.dto.CommonResponse;

public interface PushNotificationService {

	CommonResponse sendPushNotification(String token, String title, String body);

}
