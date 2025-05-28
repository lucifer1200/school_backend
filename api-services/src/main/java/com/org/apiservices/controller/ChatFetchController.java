package com.org.apiservices.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.apiservices.dto.ChatHistory;

@CrossOrigin("*")
@Slf4j
//@RestController("/api/v1/whatsapp")
@RestController
@RequestMapping("/S-Details")
@RequiredArgsConstructor
public class ChatFetchController {

	@PostMapping("/chatHistory")
	public ResponseEntity<?> FetchChatHistory(@Validated @RequestBody ChatHistory chathistory) throws Exception {
		/*PollQuestionDetails pollQuestionsList = reviewFeedbackService.getPollQuestions(chathistory);

		return ResponseEntity.ok(pollQuestionsList);*/
		return null;
	}

}
