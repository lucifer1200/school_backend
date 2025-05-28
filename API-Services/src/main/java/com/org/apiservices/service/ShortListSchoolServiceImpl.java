package com.org.apiservices.service;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.org.apiservices.constants.CustomQuery;
import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.ShortListReq;
import com.org.apiservices.dto.ShortListRespModel;
import com.org.apiservices.dto.ShortListResponse;

@Service
@Slf4j
public class ShortListSchoolServiceImpl implements ShortListSchoolService {

	private final JdbcTemplate jdbcTemplate;

	public ShortListSchoolServiceImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		new MessageConstants();
	}

	@Override
	public ShortListResponse FetchShortedSchools(ShortListReq reqObj) {
		ShortListResponse respResult = new ShortListResponse();
		List<ShortListRespModel> resultList = new ArrayList<>();

		String query = CustomQuery.SHORTED_SCHOOLS_BY_ID + "'" + reqObj.getShortListedBy() + "'"
				+ CustomQuery.SHORTED_SCHOOLS_BY_ID_CLAUSE;

		try {
			resultList = jdbcTemplate.query(query, new RowMapper<ShortListRespModel>() {
				@Override
				public ShortListRespModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					ShortListRespModel loanddetailsresp = new ShortListRespModel();
					loanddetailsresp.setId(rs.getString("ID"));
					loanddetailsresp.setSchoolAddr(rs.getString("SCHOOL_LOCATION"));
					loanddetailsresp.setSchoolCity(rs.getString("SCHOOL_CITY"));
					loanddetailsresp.setSchoolId(rs.getString("SCHOOL_ID"));
					loanddetailsresp.setSchoolName(rs.getString("SCHOOL_NAME"));
					loanddetailsresp.setSchoolPin(rs.getString("SCHOOL_PINCODE"));
					loanddetailsresp.setSchoolState(rs.getString("SCHOOL_STATE"));
					loanddetailsresp.setShortlistedDate(rs.getString("SHORTLISTED_ON"));
					loanddetailsresp.setShortListedBy(rs.getString("SHORTLISTED_BY"));
					return loanddetailsresp;
				}
			});

			if (resultList.isEmpty()) {
				respResult.setStatusCode(MessageConstants.ERROR_RESP);
				respResult.setStatusDesc("No Data Found");
			} else {
				respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
				respResult.setStatusDesc(MessageConstants.SUCCESS);
				respResult.setMessage(MessageConstants.REC_SUCCESS_MSG);
				respResult.setShortedList(resultList);
			}

		} catch (Exception e) {
			respResult.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			respResult.setStatusDesc(MessageConstants.TECH_ERROR_MESSAGE);
			e.printStackTrace();
		}

		return respResult;
	}

	@Override
	public CommonResponse InsertShortedSchools(ShortListReq reqObj) {
		CommonResponse respResult = new CommonResponse();

		String insertQuery = "INSERT INTO short_listed_school(ID, SCHOOL_ID, SCHOOL_NAME, SHORTLISTED_ON, SHORTLISTED_BY, SCHOOL_PINCODE, SCHOOL_LOCATION, SCHOOL_STATE, SCHOOL_CITY, DEL_FLAG) VALUES(?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?);";

		try {
			String id = UUID.randomUUID().toString();
			int resultStatus = jdbcTemplate.update(insertQuery, id, reqObj.getSchoolId(), reqObj.getSchoolName(),
					reqObj.getShortListedBy(), reqObj.getPincode(), reqObj.getLocation(), reqObj.getState(),
					reqObj.getCity(), "N");

			if (resultStatus > 0) {
				respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
				respResult.setStatusDesc(MessageConstants.SUCCESS);
				respResult.setMessage("Record added successfully");
			} else {
				respResult.setStatusCode(MessageConstants.ERROR_RESP);
				respResult.setStatusDesc(MessageConstants.FAILURE);
				respResult.setMessage("Exception Occured! Check if School is already shortlisted");
			}
		} catch (Exception e) {
			respResult.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			respResult.setStatusDesc(MessageConstants.TECH_ERROR_MESSAGE);
			e.printStackTrace();
		}

		return respResult;
	}

	@Override
	public CommonResponse RemoveSchoolFromList(ShortListReq reqObj) {
		CommonResponse respResult = new CommonResponse();

		String deleteQuery = CustomQuery.DELETE_SCHOOL;

		try {
			int resultStatus = jdbcTemplate.update(deleteQuery, "Y", reqObj.getSchoolId(), reqObj.getShortListedBy());

			if (resultStatus > 0) {
				respResult.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
				respResult.setStatusDesc(MessageConstants.SUCCESS);
				respResult.setMessage("Record deleted successfully");
			} else {
				respResult.setStatusCode(MessageConstants.ERROR_STATUSCODE);
				respResult.setStatusDesc(MessageConstants.ERROR);
				respResult.setMessage("Exception Occured! Check if School is already shortlisted");
			}
		} catch (Exception e) {
			respResult.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
			respResult.setStatusDesc(MessageConstants.ERROR);
			respResult.setMessage(MessageConstants.TECH_ERROR_MESSAGE);
			e.printStackTrace();
		}

		return respResult;

	}

}