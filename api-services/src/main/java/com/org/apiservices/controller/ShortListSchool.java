package com.org.apiservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.ShortListReq;
import com.org.apiservices.dto.ShortListResponse;
import com.org.apiservices.service.ShortListSchoolService;
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
public class ShortListSchool {

    private final ShortListSchoolService shortListSchoolsService;
    private final EncryptionUtils encryptionUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String invalidMsg = "Invalid service name";

    @PostMapping("/ShortedSchools")
    public ResponseEntity<?> getShortedSchools(@Validated @RequestBody String encryptedReqObj) throws Exception {
        // Decrypt the incoming request
        String decryptedReqObj = EncryptionUtils.decrypt(encryptedReqObj);
        ShortListReq reqObj = objectMapper.readValue(decryptedReqObj, ShortListReq.class);

        if ("ShortListed".equals(reqObj.getServiceName())) {
            ShortListResponse result = shortListSchoolsService.FetchShortedSchools(reqObj);
            // Encrypt the response
            String encryptedResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(encryptedResult);
        } else {
            return ResponseEntity.badRequest().body(invalidMsg);
        }
    }

    @PostMapping("/ShortList")
    public ResponseEntity<?> AddSchoolToList(@Validated @RequestBody String encryptedReqObj) throws Exception {
        // Decrypt the incoming request
        String decryptedReqObj = EncryptionUtils.decrypt(encryptedReqObj);
        ShortListReq reqObj = objectMapper.readValue(decryptedReqObj, ShortListReq.class);

        if ("addToList".equals(reqObj.getServiceName())) {
            CommonResponse result = shortListSchoolsService.InsertShortedSchools(reqObj);
            // Encrypt the response
            String encryptedResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(encryptedResult);
        } else {
            return ResponseEntity.badRequest().body(invalidMsg);
        }
    }

    @PostMapping("/RemoveSchool")
    public ResponseEntity<?> RemoveSchoolfromList(@Validated @RequestBody String encryptedReqObj) throws Exception {
        // Decrypt the incoming request
        String decryptedReqObj = EncryptionUtils.decrypt(encryptedReqObj);
        ShortListReq reqObj = objectMapper.readValue(decryptedReqObj, ShortListReq.class);

        if ("RemoveShortedSchool".equals(reqObj.getServiceName())) {
            CommonResponse result = shortListSchoolsService.RemoveSchoolFromList(reqObj);
            // Encrypt the response
            String encryptedResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(encryptedResult);
        } else {
            return ResponseEntity.badRequest().body(invalidMsg);
        }
    }
}