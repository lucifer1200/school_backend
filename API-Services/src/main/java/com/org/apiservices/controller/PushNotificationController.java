package com.org.apiservices.controller;

import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.service.PushNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class PushNotificationController {

    private final PushNotificationService pushNotificationService;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(@RequestParam String token, @RequestParam String title, @RequestParam String body) {
        CommonResponse response = pushNotificationService.sendPushNotification(token, title, body);
        return ResponseEntity.ok(response);
    }
}