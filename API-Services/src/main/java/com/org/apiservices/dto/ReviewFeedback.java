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
public class ReviewFeedback {

	@NotEmpty(message = "User-ID cannot be null or empty")
	private String userId;
	@NotEmpty(message = "Question-ID cannot be null or empty")
	private String questionId;
	@NotEmpty(message = "Schoold-ID cannot be null or empty")
	private String schoolId;
}
