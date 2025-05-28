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
public class SchoolSearchReq {
	@NotEmpty(message = "Service name should not be empty")
	public String serviceName;
	private String schoolId;
	private String pinCode;

}
