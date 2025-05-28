package com.org.apiservices.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortListReq {
	@NotEmpty(message = "Service name should not be empty")
	public String serviceName;
	private String schoolId;
	private String schoolName;
	private String pincode;
	private String location;
	private String state;
	private String district;
	private String city;
	private String shortListedBy;
	

}
