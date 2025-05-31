package com.org.apiservices.service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.org.apiservices.constants.CustomQuery;
import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.ChildReq;
import com.org.apiservices.dto.ChildRespModel;
import com.org.apiservices.dto.ChildResponse;

@Service
@Slf4j
public class ChildDetailsServiceImpl implements ChildDetailsService {

    private final JdbcTemplate jdbcTemplate;

    public ChildDetailsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        new MessageConstants();
    }

    @Override
    public ChildResponse ChildRegistration(ChildReq reqObj) {
        ChildResponse respResult = new ChildResponse();

        String insertQuery = "INSERT INTO child_details(ID, USER_REF_ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, GENDER, ADMISSION_CLASS, DOB, CREATED_DATE, HOBBIES, USER_ACTIVE, USER_PICTURE) VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?, ?, ?);";
        String userExistsQuery = "SELECT EXISTS(SELECT 1 FROM child_details WHERE ID = ? AND DEL_FLAG = 'N')";
        String updateQuery = "UPDATE child_details SET FIRST_NAME=?, MIDDLE_NAME=?, LAST_NAME=?, GENDER=?, ADMISSION_CLASS=?, DOB=?, HOBBIES=?, USER_PICTURE=?, UPDATED_ON=NOW() WHERE ID=?;";

        try {
            boolean userExists;
            try {
                userExists = jdbcTemplate.queryForObject(userExistsQuery, Boolean.class, reqObj.getRecordId());
            } catch (EmptyResultDataAccessException e) {
                userExists = false;
            }

            if (userExists) {
                int resultStatus = jdbcTemplate.update(updateQuery, reqObj.getFirstName(), reqObj.getMiddleName(), reqObj.getLastName(), reqObj.getGender(), reqObj.getAdmissionClass(), reqObj.getDob(), reqObj.getHobbies(), reqObj.getChildPic(), reqObj.getRecordId());

                if (resultStatus > 0) {
                    respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
                    respResult.setStatusDesc(MessageConstants.SUCCESS);
                    respResult.setMessage("Record updated successfully");
                } else {
                    respResult.setStatusCode(MessageConstants.ERROR_RESP);
                    respResult.setStatusDesc(MessageConstants.FAILURE);
                    respResult.setMessage("Exception Occurred!");
                }
            } else {
                String id = UUID.randomUUID().toString();
                int resultStatus = jdbcTemplate.update(insertQuery, id, reqObj.getUserId(), reqObj.getFirstName(), reqObj.getMiddleName(), reqObj.getLastName(), reqObj.getGender(), reqObj.getAdmissionClass(), reqObj.getDob(), reqObj.getHobbies(), "Y", reqObj.getChildPic());

                if (resultStatus > 0) {
                    respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
                    respResult.setStatusDesc(MessageConstants.SUCCESS);
                    respResult.setMessage("Record added successfully");
                } else {
                    respResult.setStatusCode(MessageConstants.ERROR_RESP);
                    respResult.setMessage("Exception Occurred! Check if record already exists");
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
	public ChildResponse FetchChildDetails(ChildReq reqObj) {
		// create object as per the model and bean for returning as a response
		ChildResponse resResult = new ChildResponse();
        String query = CustomQuery.FETCH_CHILD_DETAILS + "'" + reqObj.getUserId() + "'";

        System.out.println("query----" + query);

        try {
            List<ChildRespModel> results = jdbcTemplate.query(query, (rs, rowNum) -> {
            	ChildRespModel resp = new ChildRespModel();
                resp.setAdmissionClass(rs.getString("ADMISSION_CLASS"));
                resp.setChildPic(rs.getString("USER_PICTURE"));
                resp.setDob(rs.getString("DOB"));
                resp.setFirstName(rs.getString("FIRST_NAME"));
                resp.setGender(rs.getString("GENDER"));
                resp.setHobbies(rs.getString("HOBBIES"));
                resp.setLastName(rs.getString("LAST_NAME"));
                resp.setMiddleName(rs.getString("MIDDLE_NAME"));
                resp.setRecordId(rs.getString("ID"));
                resp.setCreatedOn(rs.getString("CREATED_DATE"));
                resp.setUserId(rs.getString("USER_REF_ID"));
                return resp;
            });

            if (results.isEmpty()) {
                resResult.setStatusCode(MessageConstants.ERROR_STATUSCODE);
                resResult.setStatusDesc(MessageConstants.FAILURE);
                resResult.setMessage("No Data Found");
            } else {
                resResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
                resResult.setStatusDesc(MessageConstants.SUCCESS);
                resResult.setMessage(MessageConstants.REC_SUCCESS_MSG);
                resResult.setChildDetailsRes(results);
            }

        } catch (Exception e) {
        	resResult.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
        	resResult.setStatusDesc(MessageConstants.FAILURE);
        	resResult.setMessage(MessageConstants.TECH_ERROR_MESSAGE);
            e.printStackTrace();
        }

        return resResult;
	}
}