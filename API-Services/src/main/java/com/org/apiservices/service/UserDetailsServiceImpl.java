package com.org.apiservices.service;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.org.apiservices.constants.CustomQuery;
import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.RegistrationReq;
import com.org.apiservices.dto.UserAdditionalDetailsReq;
import com.org.apiservices.dto.UserAdditionalDetailsResp;
import com.org.apiservices.dto.UserAdditionalDtlsRespModel;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final JdbcTemplate jdbcTemplate;

	public UserDetailsServiceImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		new MessageConstants();
	}

	@Override
	public CommonResponse AddUserDetailsAdditional(UserAdditionalDetailsReq reqObj) {
		CommonResponse respResult = new CommonResponse();
		
		String insertQuery = "INSERT INTO user_additional_details(ID, USER_REF_ID, QUALIFICATION, ADMISSION_CATEGORY, USER_CATEGORY, OCCUPATION, ANNUAL_INCOME, SESSION_PERIOD, STATE, CITY, LOCALITY, NEAREST_LOC, CREATED_DATE, DOB) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?);";
		String userExistsQuery = "SELECT COUNT(*) FROM user_additional_details WHERE USER_REF_ID = ?";
		String updateQuery = "UPDATE user_additional_details SET QUALIFICATION=?, ADMISSION_CATEGORY=?, USER_CATEGORY=?, OCCUPATION=?, ANNUAL_INCOME=?, SESSION_PERIOD=?, STATE=?, CITY=?, LOCALITY=?, NEAREST_LOC=?, UPDATED_ON=NOW(), DOB=? WHERE USER_REF_ID=?;";

		try {
			Integer userCount = null;
			try {
				userCount = jdbcTemplate.queryForObject(userExistsQuery, new Object[] { reqObj.getUserId() },
						Integer.class);
			} catch (EmptyResultDataAccessException e) {
				userCount = 0;
			}

			if (userCount != null && userCount > 0) {
				int resultStatus = jdbcTemplate.update(updateQuery, reqObj.getQualification(),
						reqObj.getAddmissionCategory(), reqObj.getUserCat(), reqObj.getOccupation(),
						reqObj.getAnnualIncome(), reqObj.getSessionPeriod(), reqObj.getState(), reqObj.getCity(),
						reqObj.getLocality(), reqObj.getLandmark(), reqObj.getDob(), reqObj.getUserId());

				if (resultStatus > 0) {
					respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
					respResult.setStatusDesc(MessageConstants.SUCCESS);
					respResult.setMessage("Record updated successfully");
				} else {
					respResult.setStatusCode(MessageConstants.ERROR_RESP);
					respResult.setMessage("Exception Occurred!");
					respResult.setStatusDesc(MessageConstants.FAILURE);
				}
			} else {
				String id = UUID.randomUUID().toString();
				int resultStatus = jdbcTemplate.update(insertQuery, id, reqObj.getUserId(), reqObj.getQualification(),
						reqObj.getAddmissionCategory(), reqObj.getUserCat(), reqObj.getOccupation(),
						reqObj.getAnnualIncome(), reqObj.getSessionPeriod(), reqObj.getState(), reqObj.getCity(),
						reqObj.getLocality(), reqObj.getLandmark(), reqObj.getDob());

				if (resultStatus > 0) {
					respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
					respResult.setStatusDesc(MessageConstants.SUCCESS);
					respResult.setMessage("Record added successfully");
				} else {
					respResult.setStatusCode(MessageConstants.ERROR_RESP);
					respResult.setMessage("Exception Occurred!");
					respResult.setStatusDesc(MessageConstants.FAILURE);
				}
			}
		} catch (Exception e) {
			respResult.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			respResult.setStatusDesc(MessageConstants.FAILURE);
			respResult.setMessage(MessageConstants.TECH_ERROR_MESSAGE);
			e.printStackTrace();
		}

		return respResult;
	}

	@Override
	public UserAdditionalDetailsResp GetUserDetails(UserAdditionalDetailsReq reqObj) {
		UserAdditionalDetailsResp respResult = new UserAdditionalDetailsResp();
		List<UserAdditionalDtlsRespModel> listDetails;

		String query = CustomQuery.FETCH_USER_ADDITIONAL_DETAILS + "'" + reqObj.getUserId() + "'"
				+ CustomQuery.DEL_FLAG_CLAUSE;

		try {
			listDetails = jdbcTemplate.query(query, new RowMapper<UserAdditionalDtlsRespModel>() {
				@Override
				public UserAdditionalDtlsRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserAdditionalDtlsRespModel resp = new UserAdditionalDtlsRespModel();
					resp.setUserId(rs.getString("ID"));
					resp.setCreatedOn(rs.getString("CREATED_DATE"));
					resp.setAddmissionCategory(rs.getString("ADMISSION_CATEGORY"));
					resp.setAnnualIncome(rs.getString("ANNUAL_INCOME"));
					resp.setCity(rs.getString("CITY"));
					resp.setDob(rs.getString("DOB"));
					resp.setLandmark(rs.getString("NEAREST_LOC"));
					resp.setLocality(rs.getString("LOCALITY"));
					resp.setOccupation(rs.getString("OCCUPATION"));
					resp.setQualification(rs.getString("QUALIFICATION"));
					resp.setSessionPeriod(rs.getString("SESSION_PERIOD"));
					resp.setState(rs.getString("STATE"));
					return resp;
				}
			});

			if (listDetails.isEmpty()) {
				respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
				respResult.setMessage("No data found");
				respResult.setStatusDesc(MessageConstants.SUCCESS);
			} else {
				respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
				respResult.setStatusDesc(MessageConstants.SUCCESS);
				respResult.setMessage("Record fetched successfully");
				respResult.setUserAdditionalRes(listDetails);
			}

		} catch (Exception e) {
			respResult.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			respResult.setStatusDesc(MessageConstants.FAILURE);
			respResult.setMessage(MessageConstants.TECH_ERROR_MESSAGE);
			e.printStackTrace();
		}

		return respResult;
	}

	@Override
	public CommonResponse UpdateUserDetails(RegistrationReq reqObj) {

		CommonResponse respResult = new CommonResponse();

		String userExistsQuery = "SELECT COUNT(*) FROM user_details WHERE USER_ID = ? AND USER_ACTIVE = 'Y'";
		String updateQuery = "UPDATE user_details SET FIRST_NAME=?, LAST_NAME=?, USER_EMAIL1=?, USER_MOBILE1=?, USER_PICTURE=?, UPDATED_ON=NOW(), GENDER=? WHERE USER_ID=?;";

		try {
			Integer userCount = null;
			try {
				userCount = jdbcTemplate.queryForObject(userExistsQuery, new Object[] { reqObj.getUserId() },
						Integer.class);
			} catch (EmptyResultDataAccessException e) {
				userCount = 0;
			}

			if (userCount != null && userCount > 0) {
				int resultStatus = jdbcTemplate.update(updateQuery, reqObj.getFirstName(), reqObj.getLastName(),
						reqObj.getEmail(), reqObj.getMobNumber(), reqObj.getUserPic(), reqObj.getGender(),
						reqObj.getUserId());

				if (resultStatus > 0) {
					respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
					respResult.setStatusDesc(MessageConstants.SUCCESS);
					respResult.setMessage("Record updated successfully");
				} else {
					respResult.setStatusCode(MessageConstants.ERROR_RESP);
					respResult.setMessage("Exception Occurred!");
					respResult.setStatusDesc(MessageConstants.FAILURE);
				}
			} else {
				respResult.setStatusCode(MessageConstants.ERROR_RESP);
				respResult.setStatusDesc(MessageConstants.FAILURE);
				respResult.setMessage("User not exists!");
			}
		} catch (Exception e) {
			respResult.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			respResult.setStatusDesc(MessageConstants.FAILURE);
			respResult.setMessage(MessageConstants.TECH_ERROR_MESSAGE);
			e.printStackTrace();
		}

		return respResult;

	}

}