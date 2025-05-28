package com.org.apiservices.constants;

public final class CustomQuery {

	public final String pendigLan = "select * from mfi_stagging_loandetails where LoanStatusID != 'UNDB' and CreatedBy =";
	public final String clause = "and RejectReasonID  is null or RejectReasonID = ''";
	public final String lanDetails = "select * from mfi_stagging_loandetails where LoanAccountNo = ";
	public final String lanClause = "and CreatedBy =";

	public static final String ALL_SCHOOLS_QUERY = "select * from school_data where address like '%";
	public static final String SCHOOLS_BY_ID = "select * from school_data where id =";
	public static final String SHORTED_SCHOOLS_BY_ID = "select * from short_listed_school where SHORTLISTED_BY=";
	public static final String SHORTED_SCHOOLS_BY_ID_CLAUSE = "and DEL_FLAG !='Y' OR DEL_FLAG is null";
	public static final String VALIDATE_LOGIN_PIN = "select * from user_details where USER_PIN=";
	public static final String VALIDATE_LOGIN_PIN_CLAUSE = "and USER_MOBILE1 =";
	public static final String DELETE_SCHOOL = "UPDATE short_listed_school SET DEL_FLAG=? WHERE SCHOOL_ID=? AND SHORTLISTED_BY=?;";
	
	public static final String FETCH_CHILD_DETAILS = "select * from child_details where USER_REF_ID=";
	public static final String FETCH_USER_ADDITIONAL_DETAILS = "select * from user_additional_details where USER_REF_ID=";
	public static final String DEL_FLAG_CLAUSE = "and DEL_FLAG !='Y' ";
	public static final String IS_ACTIVE_CLAUSE = "and USER_ACTIVE ='Y' ";
	public static final String USER_ADDITIONAL_EXISTS = "select USER_REF_ID from user_additional_details where USER_REF_ID=";
	public static final String CHILD_DETAILS_EXISTS = "select ID from child_details where ID=";
	public static final String USER_EXISTS = "select USER_ID from user_details where USER_ID=";
	

	public CustomQuery() {
	}

}
