package com.org.apiservices.service;

import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.PollQuestionDetails;
import com.org.apiservices.dto.PollQuestions;
import com.org.apiservices.dto.RecordUserFeedback;
import com.org.apiservices.dto.ReviewFeedback;
import com.org.apiservices.dto.ReviewFeedbackResult;

public interface ReviewFeedbackService {

	PollQuestionDetails getPollQuestions(PollQuestions pollQuestions);

	CommonResponse recordUserFeedback(RecordUserFeedback userFeedback);

	ReviewFeedbackResult reviewFeedbackDetails(ReviewFeedback reviewfeedback);

}
