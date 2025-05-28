package com.org.apiservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildRespModel {

	private String userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String admissionClass;
	private String dob;
	private String hobbies;
	private String childPic;
	private String CreatedOn;
	private String recordId;
	
	
}
