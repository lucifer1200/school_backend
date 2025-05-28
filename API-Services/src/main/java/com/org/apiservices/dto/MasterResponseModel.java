package com.org.apiservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasterResponseModel {

	private String propetyName;
	private String propVal;
	private String propDesc;
	private String propUpdateDate;

}
