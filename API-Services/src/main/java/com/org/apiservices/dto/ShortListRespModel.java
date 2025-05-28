package com.org.apiservices.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortListRespModel {

	private String schoolId;

	private String id;

	private String ShortListedBy;

	private String schoolName;

	private String shortlistedDate;

	private String schoolPin;

	private String schoolAddr;

	private String schoolState;

	private String schoolCity;

}
