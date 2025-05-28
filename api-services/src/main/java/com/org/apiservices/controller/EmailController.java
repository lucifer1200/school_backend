package com.org.apiservices.controller;

import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.EmailReq;
import com.org.apiservices.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;

	@PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody EmailReq emailRequest) {
        // Assuming emailService.sendEmail() processes the email request
        CommonResponse response = emailService.sendEmail(
            emailRequest.getTo(),
            emailRequest.getSubject(),
            emailRequest.getText()
        );
        return ResponseEntity.ok(response);
    }
}