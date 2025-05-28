package com.org.apiservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.apiservices.dto.SchoolResponse;
import com.org.apiservices.dto.SchoolSearchReq;
import com.org.apiservices.security.JwtTokenUtil;
import com.org.apiservices.service.FetchSchoolsService;
import com.org.apiservices.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/S-Details")
@RequiredArgsConstructor
public class FetchSchools {
	private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
	private final FetchSchoolsService fetchSchoolsService;
	private final EncryptionUtils encryptionUtils;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/All-S")
	public ResponseEntity<?> AllSchools(@Validated @RequestBody String encryptedSchoolReq) throws Exception {

		// Decrypt the incoming request
		String decryptedSchoolReq = EncryptionUtils.decrypt(encryptedSchoolReq);
		SchoolSearchReq schoolReq = objectMapper.readValue(decryptedSchoolReq, SchoolSearchReq.class);

		// Process the request
		SchoolResponse schools = fetchSchoolsService.getAllSchools(schoolReq);

		// Encrypt the response
		String encryptedSchools = encryptionUtils.encrypt(objectMapper.writeValueAsString(schools));
		return ResponseEntity.ok(encryptedSchools);

	}
}