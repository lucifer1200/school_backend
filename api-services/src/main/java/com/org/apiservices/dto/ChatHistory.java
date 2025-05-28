package com.org.apiservices.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatHistory {
	@NotEmpty(message = "Login-User-ID cannot be null or empty")
	private String loginUserId;
	//@NotEmpty(message = "Chat-User-ID cannot be null or empty")
	private String chatUserId;
}
