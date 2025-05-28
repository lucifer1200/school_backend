package com.org.apiservices.service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.PollQuestionDetails;
import com.org.apiservices.dto.PollQuestions;
import com.org.apiservices.dto.RecordUserFeedback;
import com.org.apiservices.dto.ReviewFeedback;
import com.org.apiservices.dto.ReviewFeedbackResult;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ReviewFeedbackServiceImpl<JSONObject> implements ReviewFeedbackService {

	private final JdbcTemplate jdbcTemplate;

	public ReviewFeedbackServiceImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		new MessageConstants();

	}

	@Override
	public PollQuestionDetails getPollQuestions(PollQuestions pollQuestions) {

		PollQuestionDetails pollQuestionsDetails = new PollQuestionDetails();

		final String allQuestionsQuery = "SELECT rq.*, uf.USER_FEEDBACK_VALUE " + "FROM REVIEW_QUESTIONS rq "
				+ "LEFT JOIN USERS_FEEDBACK uf " + "ON rq.ID = uf.QUESTION_ID " + "AND uf.USER_ID = ?";
		try {
			List<Map<String, Object>> allQuestions = jdbcTemplate.queryForList(allQuestionsQuery,
					pollQuestions.getUserId());

			pollQuestionsDetails.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
			pollQuestionsDetails.setStatusDesc(MessageConstants.SUCCESS);
			pollQuestionsDetails.setMessage(MessageConstants.REC_SUCCESS_MSG);
			pollQuestionsDetails.setPollQuestions(allQuestions);

		} catch (Exception e) {
			pollQuestionsDetails.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			pollQuestionsDetails.setStatusDesc(MessageConstants.ERROR);
			pollQuestionsDetails.setTechErrorMsg(e.toString());

		}

		return pollQuestionsDetails;
	}

	@Override
	public CommonResponse recordUserFeedback(RecordUserFeedback userFeedback) {
		int result = 0;
		CommonResponse serviceResponse = new CommonResponse();
		try {
			String uniqueId = UUID.randomUUID().toString();
			String recordCheckQuery = "SELECT ID FROM USERS_FEEDBACK WHERE QUESTION_ID = '"
					+ userFeedback.getQuestionId() + "' AND SCHOOL_ID = '" + userFeedback.getSchoolId()
					+ "' AND USER_ID = '" + userFeedback.getUserId() + "'";

			List<Map<String, Object>> recordExists = jdbcTemplate.queryForList(recordCheckQuery);

			if (recordExists.isEmpty()) {
				final String userfeedbacksql = "INSERT INTO USERS_FEEDBACK (ID, QUESTION_ID, SCHOOL_ID, USER_ID, USER_FEEDBACK_VALUE, CREATED_DATE) VALUES (?, ?, ?, ?, ?, NOW())";
				result = jdbcTemplate.update(userfeedbacksql, uniqueId, userFeedback.getQuestionId(),
						userFeedback.getSchoolId(), userFeedback.getUserId(), userFeedback.getFeedbackText());

			} else {
				String feedBackupdate = "UPDATE USERS_FEEDBACK SET USER_FEEDBACK_VALUE = ?, UPDATED_ON = NOW() WHERE QUESTION_ID = ? AND SCHOOL_ID = ? AND USER_ID = ? ";
				result = jdbcTemplate.update(feedBackupdate, userFeedback.getFeedbackText(),
						userFeedback.getQuestionId(), userFeedback.getSchoolId(), userFeedback.getUserId());

			}
			if (result == 1) {
				serviceResponse.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
				serviceResponse.setStatusDesc(MessageConstants.SUCCESS);
				serviceResponse.setMessage("Records inserted/update succesfully");
			}
		} catch (Exception e) {
			serviceResponse.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			serviceResponse.setStatusDesc(MessageConstants.ERROR);
			serviceResponse.setTechErrorMsg(e.toString());
		}

		return serviceResponse;
	}

	@Override
	public ReviewFeedbackResult reviewFeedbackDetails(ReviewFeedback reviewfeedback) {

		ReviewFeedbackResult reviewfeedbackDetails = new ReviewFeedbackResult();
		String[] allValues = null;
		List<JsonNode> responseList = new ArrayList<>();
		try {
			final String options = "SELECT OPTIONS FROM REVIEW_QUESTIONS WHERE ID ='" + reviewfeedback.getQuestionId()
					+ "'";
			String optionsVal = jdbcTemplate.queryForObject(options, String.class);
			allValues = optionsVal.split(",");

		} catch (Exception e) {
			reviewfeedbackDetails.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			reviewfeedbackDetails.setStatusDesc(MessageConstants.ERROR);
			reviewfeedbackDetails.setTechErrorMsg(e.toString());
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		ArrayNode jsonArray = mapper.createArrayNode();

		for (String element : allValues) {
			ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
			try {

				String valueCountQuery = "SELECT COUNT(*) FROM USERS_FEEDBACK " + "WHERE USER_FEEDBACK_VALUE = '"
						+ element + "' AND QUESTION_ID = '" + reviewfeedback.getQuestionId() + "'";
				log.info(element, valueCountQuery);
				String answerUserId = "SELECT ud.FIRST_NAME, ud.LAST_NAME, ud.USER_ID " + "FROM USER_DETAILS ud "
						+ "WHERE ud.USER_ID IN ( " + "SELECT uf.USER_ID " + "FROM USERS_FEEDBACK uf "
						+ "WHERE uf.USER_FEEDBACK_VALUE = ? AND uf.QUESTION_ID = ?)";
				int valueCount = jdbcTemplate.queryForObject(valueCountQuery, Integer.class);
				jsonObject.put(element.trim(), valueCount);

				List<Map<String, Object>> userIds = jdbcTemplate.queryForList(answerUserId, element,
						reviewfeedback.getQuestionId());

				if (userIds != null && !userIds.isEmpty()) {
					StringBuilder sb = new StringBuilder();
					for (Map<String, Object> userDetails : userIds) {
						String firstName = (String) userDetails.get("FIRST_NAME");
						String lastName = (String) userDetails.get("LAST_NAME");
						String userId = (String) userDetails.get("USER_ID");

						sb.append(firstName).append(" ").append(lastName).append(" | ").append(userId).append(", ");
					}
					if (sb.length() > 2) {
						sb.delete(sb.length() - 2, sb.length());
					}
					jsonObject.put("reviewedUserDetails", sb.toString());
				} else {
					jsonObject.put("reviewedUserDetails", "");
				}
				responseList.add(jsonObject);
			} catch (Exception e) {
				reviewfeedbackDetails.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
				reviewfeedbackDetails.setStatusDesc(MessageConstants.ERROR);
				reviewfeedbackDetails.setTechErrorMsg(e.toString());
				e.printStackTrace();
			}
			jsonArray.add(jsonObject); // add object to array
		}

		ObjectNode jsonObject = JsonNodeFactory.instance.objectNode();
		try {

			String totReviewCount = "SELECT COUNT(*) FROM USERS_FEEDBACK WHERE QUESTION_ID = '"
					+ reviewfeedback.getQuestionId() + "'";

			int valueCount = jdbcTemplate.queryForObject(totReviewCount, Integer.class);

			jsonObject.put("totReviewCount", valueCount);
			responseList.add(jsonObject);

			reviewfeedbackDetails.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
			reviewfeedbackDetails.setStatusDesc(MessageConstants.SUCCESS);

			reviewfeedbackDetails.setFeedbackDetails(responseList);
		} catch (Exception e) {
			reviewfeedbackDetails.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			reviewfeedbackDetails.setStatusDesc(MessageConstants.ERROR);
			reviewfeedbackDetails.setTechErrorMsg(e.toString());
			e.printStackTrace();
		}

		return reviewfeedbackDetails;

	}

}
