package com.org.apiservices.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewFeedbackResult {

	private String statusCode;
	private String statusDesc;
	private String message;
	private String techErrorMsg;
	private List<?> feedbackDetails;

}
