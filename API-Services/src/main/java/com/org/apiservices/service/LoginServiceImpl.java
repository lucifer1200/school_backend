package com.org.apiservices.service;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.org.apiservices.constants.CustomQuery;
import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.LoginReq;
import com.org.apiservices.dto.LoginResponse;
import com.org.apiservices.dto.LoginResponseModel;
import com.org.apiservices.dto.RegistrationReq;
import com.org.apiservices.security.JwtTokenUtil;
//import com.org.apiservices.security.JwtTokenUtil;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	private final JdbcTemplate jdbcTemplate;

	public LoginServiceImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		new MessageConstants();
	}

	@Override
	public LoginResponse validateLogin(LoginReq reqObj) {

		final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
		String token = "";

		LoginResponse respObj = new LoginResponse();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String timestamp = LocalDateTime.now().format(formatter);

		String query = CustomQuery.VALIDATE_LOGIN_PIN + " '" + reqObj.getPin() + "' "
				+ CustomQuery.VALIDATE_LOGIN_PIN_CLAUSE + reqObj.getMobNumber();
		String updateQuery = "UPDATE user_details SET USER_LOGIN_STATUS=?, AUTH_TOKEN_EXPIRED=?, LAST_LOGIN=?, AUTH_TIMESTAMP=?, AUTH_TOKEN=? WHERE USER_MOBILE1=?";

		System.out.println("query----" + query);

		try {
			List<LoginResponseModel> loginResults = jdbcTemplate.query(query, new RowMapper<LoginResponseModel>() {
				@Override
				public LoginResponseModel mapRow(ResultSet rs, int rowNum) throws SQLException {

					// Create claims
					Map<String, Object> claims = new HashMap<>();
					claims.put("role", reqObj.getRoles());
					claims.put("mob", reqObj.getMobNumber());

					// Generate token
					String token = jwtTokenUtil.generateToken(claims, reqObj.getMobNumber());

					LoginResponseModel resp = new LoginResponseModel();
					resp.setFirstName(rs.getString("FIRST_NAME"));
					resp.setEmail(rs.getString("USER_EMAIL1"));
					resp.setLastName(rs.getString("LAST_NAME"));
					resp.setMobNumber(rs.getString("USER_MOBILE1"));
					resp.setUserId(rs.getString("USER_ID"));
					resp.setUserMaker(rs.getString("USER_ROLE_MAKER"));
					resp.setUserChecker(rs.getString("USER_ROLE_CHECKER"));
					resp.setUserPic(rs.getString("USER_PICTURE"));
					resp.setAuthToken(token);
					resp.setAppVersion(rs.getString("USER_APP_VERSION"));
					resp.setLastLogin(timestamp);
					return resp;
				}
			});

			if (loginResults.isEmpty()) {
				respObj.setStatusCode(MessageConstants.SYNC_STATUSCODE1);
				respObj.setStatusDesc("Invalid Credentials");
				return respObj;
			} else {
				int resultStatus = jdbcTemplate.update(updateQuery, "Y", "N", timestamp, timestamp, token,
						reqObj.getMobNumber());
				if (resultStatus > 0) {
					respObj.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
					respObj.setStatusDesc(MessageConstants.SUCCESS);
					respObj.setMessage(MessageConstants.REC_SUCCESS_MSG);
					respObj.setUserDetails(loginResults);
				} else {
					respObj.setStatusCode(MessageConstants.SYNC_STATUSCODE);
					respObj.setMessage("Issue in updating the login status");
					respObj.setStatusDesc(MessageConstants.FAILURE);

				}
			}

		} catch (Exception e) {
			log.error("Error validating login", e);
			respObj.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			respObj.setStatusDesc(MessageConstants.FAILURE);
			respObj.setMessage(MessageConstants.TECH_ERROR_MESSAGE);
		}

		return respObj;
	}

	@Override
	public CommonResponse register(RegistrationReq reqObj) {

		CommonResponse resObj = new CommonResponse();

		String insertQuery = "INSERT INTO user_details(USER_ID,USER_NAME,FIRST_NAME,LAST_NAME,USER_EMAIL1,USER_MOBILE1,APP_ID,USER_PIN,USER_ACTIVE,USER_PICTURE,USER_APP_VERSION,USER_LOGIN_STATUS,USER_DOB,USER_CREATE_TS,USER_ROLE_MAKER,USER_ROLE_CHECKER,LAST_LOGIN) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?);";

		System.out.println("query----" + insertQuery);

		try {
			String id = UUID.randomUUID().toString();
			int resultStatus = jdbcTemplate.update(insertQuery, id, reqObj.getEmail(), reqObj.getFirstName(),
					reqObj.getLastName(), reqObj.getEmail(), reqObj.getMobNumber(), reqObj.getAppId(), reqObj.getPin(),
					"N", reqObj.getUserPic(), reqObj.getUserAppVersion(), "N", reqObj.getUserDob(), reqObj.getMaker(),
					reqObj.getChecker(), null);

			if (resultStatus > 0) {
				resObj.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
				resObj.setStatusDesc(MessageConstants.SUCCESS);
			} else {
				resObj.setStatusCode(MessageConstants.SYNC_STATUSCODE);
				resObj.setStatusDesc(MessageConstants.FAILURE);
				resObj.setMessage("Exception Occured! Please check");
			}

		} catch (Exception e) {
			log.error("Error ", e);
			resObj.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			resObj.setStatusDesc(MessageConstants.FAILURE);
			resObj.setMessage(MessageConstants.TECH_ERROR_MESSAGE);
			e.printStackTrace();
		}

		return resObj;
	}
}