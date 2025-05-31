package com.org.apiservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PollQuestionDetails {
	private String statusCode;
	private String statusDesc;
	private String message;
	private String techErrorMsg;
	private List<?> pollQuestions;

}
