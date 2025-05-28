package com.org.apiservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.apiservices.dto.*;
import com.org.apiservices.service.UserDetailsService;
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
public class UserDetails {

    private final UserDetailsService userDetailsService;
    private final EncryptionUtils encryptionUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String invalidMsg = "Invalid service name";

    @PostMapping("/UserAdditionalDetails")
    public ResponseEntity<?> AddChildDetails(@Validated @RequestBody String encryptedReqObj) throws Exception {
        // Decrypt the incoming request
        String decryptedReqObj = EncryptionUtils.decrypt(encryptedReqObj);
        UserAdditionalDetailsReq reqObj = objectMapper.readValue(decryptedReqObj, UserAdditionalDetailsReq.class);

        if ("UserAdditionalDetails".equals(reqObj.getServiceName())) {
            CommonResponse result = userDetailsService.AddUserDetailsAdditional(reqObj);
            // Encrypt the response
            String encryptedResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(encryptedResult);
        } else {
            return ResponseEntity.badRequest().body(invalidMsg);
        }
    }

    @PostMapping("/getUserAdditionalDetails")
    public ResponseEntity<?> GetUserAdditionalDetails(@Validated @RequestBody String encryptedReqObj) throws Exception {
        // Decrypt the incoming request
        String decryptedReqObj = EncryptionUtils.decrypt(encryptedReqObj);
        UserAdditionalDetailsReq reqObj = objectMapper.readValue(decryptedReqObj, UserAdditionalDetailsReq.class);

        if ("getUserAdditionalDetails".equals(reqObj.getServiceName())) {
            UserAdditionalDetailsResp result = userDetailsService.GetUserDetails(reqObj);
            // Encrypt the response
            String encryptedResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(encryptedResult);
        } else {
            return ResponseEntity.badRequest().body(invalidMsg);
        }
    }

    @PostMapping("/UpdateUserDetails")
    public ResponseEntity<?> UpdateUserDetails(@Validated @RequestBody String encryptedReqObj) throws Exception {
        // Decrypt the incoming request
        String decryptedReqObj = EncryptionUtils.decrypt(encryptedReqObj);
        RegistrationReq reqObj = objectMapper.readValue(decryptedReqObj, RegistrationReq.class);

        if ("UpdateUserDetails".equals(reqObj.getServiceName())) {
            CommonResponse result = userDetailsService.UpdateUserDetails(reqObj);
            // Encrypt the response
            String encryptedResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(encryptedResult);
        } else {
            return ResponseEntity.badRequest().body(invalidMsg);
        }
    }
}