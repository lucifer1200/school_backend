package com.org.apiservices.service;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.org.apiservices.constants.CustomQuery;
import com.org.apiservices.constants.MessageConstants;
import com.org.apiservices.dto.SchoolRespModel;
import com.org.apiservices.dto.SchoolResponse;
import com.org.apiservices.dto.SchoolSearchReq;

@Service
@Slf4j
public class FetchSchoolsServiceImpl implements FetchSchoolsService {

    private final JdbcTemplate jdbcTemplate;

    public FetchSchoolsServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        new MessageConstants();
    }

    @Override
    public SchoolResponse getAllSchools(SchoolSearchReq schoolReq) {

        SchoolResponse schoolResp = new SchoolResponse();
        List<SchoolRespModel> listDetails = new ArrayList<>();
        StringBuilder query = new StringBuilder();

        try {
            if ("getSchoolDetails".equalsIgnoreCase(schoolReq.getServiceName())) {
                query.append(CustomQuery.ALL_SCHOOLS_QUERY).append(schoolReq.getPinCode()).append("%'");
            } else if ("staticSchools".equalsIgnoreCase(schoolReq.getServiceName())) {
                query.append(CustomQuery.ALL_SCHOOLS_QUERY).append(schoolReq.getPinCode()).append("%' limit 3");
            } else if ("searchschoolById".equalsIgnoreCase(schoolReq.getServiceName())) {
                query.append(CustomQuery.SCHOOLS_BY_ID).append(schoolReq.getSchoolId());
            } else {
            	schoolResp.setStatusCode(MessageConstants.SERV_NAME_ERROR);
                schoolResp.setStatusDesc(MessageConstants.SERVICE_NOT_FOUND);
                
                return schoolResp;
            }

            listDetails = jdbcTemplate.query(query.toString(), schoolRowMapper);
            schoolResp.setStatusCode(MessageConstants.SUCCESS_STATUSCODE);
            schoolResp.setStatusDesc(MessageConstants.SUCCESS);
            schoolResp.setMessage(MessageConstants.REC_SUCCESS_MSG);
            schoolResp.setSchooldetailslist(listDetails);

        } catch (Exception e) {
            log.error("Error fetching school details", e);
            schoolResp.setStatusCode(MessageConstants.TECH_ERROR_STATUSCODE);
            schoolResp.setStatusDesc(MessageConstants.ERROR);
            schoolResp.setTechErrorMsg(e.toString());
        }

        return schoolResp;
    }

    private final RowMapper<SchoolRespModel> schoolRowMapper = (rs, rowNum) -> {
        SchoolRespModel tempResponse = new SchoolRespModel();
        tempResponse.setAbout(rs.getString("about"));
        tempResponse.setAcademic_session(rs.getString("academic_session"));
        tempResponse.setAddress(rs.getString("address"));
        tempResponse.setAdmissions(rs.getString("admissions"));
        tempResponse.setBoards(rs.getString("boards"));
        tempResponse.setBoards_slug(rs.getString("boards_slug"));
        tempResponse.setBrochure(rs.getString("brochure"));
        tempResponse.setBuilt_in_area(rs.getString("built_in_area"));
        tempResponse.setClasses(rs.getString("classes"));
        tempResponse.setClasses_offered(rs.getString("classes_offered"));
        tempResponse.setCoed_status(rs.getString("coed_status"));
        tempResponse.setDay_scholars_allowed(rs.getString("day_scholars_allowed"));
        tempResponse.setDay_wise_schedule(rs.getString("day_wise_schedule"));
        tempResponse.setEmail(rs.getString("email"));
        tempResponse.setFaqs(rs.getString("faqs"));
        tempResponse.setFeature_facilities(rs.getString("feature_facilities"));
        tempResponse.setFeeAvailableSessValues(rs.getString("feeAvailableSessValues"));
        tempResponse.setFees_structure(rs.getString("fees_structure"));
        tempResponse.setFood(rs.getString("food"));
        tempResponse.setFormat(rs.getString("format"));
        tempResponse.setGallery(rs.getString("gallery"));
        tempResponse.setId(rs.getString("id"));
        tempResponse.setInfrastruture(rs.getString("infrastruture"));
        tempResponse.setInternal(rs.getString("internal"));
        tempResponse.setLanguages_taught(rs.getString("languages_taught"));
        tempResponse.setLevel(rs.getString("level"));
        tempResponse.setMedium(rs.getString("medium"));
        tempResponse.setMyUnknownColumn(rs.getString("MyUnknownColumn"));
        tempResponse.setName(rs.getString("name"));
        tempResponse.setNearby_locations(rs.getString("nearby_locations"));
        tempResponse.setNumber_of_students(rs.getString("number_of_students"));
        tempResponse.setOwnership(rs.getString("ownership"));
        tempResponse.setPhoneNo(rs.getString("phone_no"));
        tempResponse.setReviews(rs.getString("reviews"));
        tempResponse.setScholarship_program(rs.getString("scholarship_program"));
        tempResponse.setSchool_results(rs.getString("school_results"));
        tempResponse.setSchoolName(rs.getString("School Name"));
        tempResponse.setShortName(rs.getString("short_name"));
        tempResponse.setSlug(rs.getString("slug"));
        tempResponse.setStudent_teacher_ratio(rs.getString("student_teacher_ratio"));
        tempResponse.setTimings(rs.getString("timings"));
        tempResponse.setTopper_and_achievers(rs.getString("topper_and_achievers"));
        tempResponse.setVerified_by_school(rs.getString("verified_by_school"));
        tempResponse.setViews(rs.getString("views"));
        tempResponse.setWebsite(rs.getString("website"));
        tempResponse.setYear_of_establishment(rs.getString("year_of_establishment"));
        return tempResponse;
    };
}