package com.org.apiservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.apiservices.dto.*;
import com.org.apiservices.service.ReviewFeedbackService;
import com.org.apiservices.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/S-Details")
@RequiredArgsConstructor
public class ReviewFeedbackController {

    private final ReviewFeedbackService reviewFeedbackService;
    private final EncryptionUtils encryptionUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/questionsList")
    public ResponseEntity<?> QuestionsList(@Validated @RequestBody String encryptedPollQuestions) throws Exception {
        // Decrypt the incoming request
        String decryptedPollQuestions = EncryptionUtils.decrypt(encryptedPollQuestions);
        PollQuestions pollQuestions = objectMapper.readValue(decryptedPollQuestions, PollQuestions.class);

        // Process the request
        PollQuestionDetails pollQuestionsList = reviewFeedbackService.getPollQuestions(pollQuestions);

        // Encrypt the response
        String encryptedPollQuestionsList = encryptionUtils.encrypt(objectMapper.writeValueAsString(pollQuestionsList));
        return ResponseEntity.ok(encryptedPollQuestionsList);
    }

    @PostMapping("/recordUserFeedback")
    public ResponseEntity<?> RecordUserFeedBack(@Validated @RequestBody String encryptedUserFeedback) throws Exception {
        // Decrypt the incoming request
        String decryptedUserFeedback = EncryptionUtils.decrypt(encryptedUserFeedback);
        RecordUserFeedback userFeedback = objectMapper.readValue(decryptedUserFeedback, RecordUserFeedback.class);

        // Process the request
        CommonResponse userFeedbackService = reviewFeedbackService.recordUserFeedback(userFeedback);

        // Encrypt the response
        String encryptedUserFeedbackService = encryptionUtils.encrypt(objectMapper.writeValueAsString(userFeedbackService));
        return ResponseEntity.ok(encryptedUserFeedbackService);
    }

    @PostMapping("/reviewDetails")
    public ResponseEntity<?> ReviewFeedbackDetails(@Validated @RequestBody String encryptedReviewFeedback) throws Exception {
        // Decrypt the incoming request
        String decryptedReviewFeedback = EncryptionUtils.decrypt(encryptedReviewFeedback);
        ReviewFeedback reviewFeedback = objectMapper.readValue(decryptedReviewFeedback, ReviewFeedback.class);

        log.info(reviewFeedback.getQuestionId());
        log.info(reviewFeedback.getSchoolId());
        log.info(reviewFeedback.getUserId());

        // Process the request
        ReviewFeedbackResult reviewFeedbackResult = reviewFeedbackService.reviewFeedbackDetails(reviewFeedback);

        // Encrypt the response
        String encryptedReviewFeedbackResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(reviewFeedbackResult));
        return ResponseEntity.ok(encryptedReviewFeedbackResult);
    }
}