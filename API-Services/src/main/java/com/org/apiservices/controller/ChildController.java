package com.org.apiservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.apiservices.dto.ChildReq;
import com.org.apiservices.dto.ChildResponse;
import com.org.apiservices.service.ChildDetailsService;
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
public class ChildController {

    private final ChildDetailsService childDetailsService;
    private final EncryptionUtils encryptionUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String invalidMsg = "Invalid service name";

    @PostMapping("/RegisterChild")
    public ResponseEntity<?> AddChildDetails(@Validated @RequestBody String encryptedReqObj) throws Exception {
        // Decrypt the incoming request
        String decryptedReqObj = EncryptionUtils.decrypt(encryptedReqObj);
        ChildReq reqObj = objectMapper.readValue(decryptedReqObj, ChildReq.class);

        if ("childRegister".equals(reqObj.getServiceName())) {
            ChildResponse result = childDetailsService.ChildRegistration(reqObj);
            // Encrypt the response
            String encryptedResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(encryptedResult);
        } else {
            return ResponseEntity.badRequest().body(invalidMsg);
        }
    }

    @PostMapping("/ChildDetails")
    public ResponseEntity<?> getChildDetails(@Validated @RequestBody String encryptedReqObj) throws Exception {
        // Decrypt the incoming request
        String decryptedReqObj = EncryptionUtils.decrypt(encryptedReqObj);
        ChildReq reqObj = objectMapper.readValue(decryptedReqObj, ChildReq.class);

        if ("getChildDetails".equals(reqObj.getServiceName())) {
            ChildResponse result = childDetailsService.FetchChildDetails(reqObj);
            // Encrypt the response
            String encryptedResult = encryptionUtils.encrypt(objectMapper.writeValueAsString(result));
            return ResponseEntity.ok(encryptedResult);
        } else {
            return ResponseEntity.badRequest().body(invalidMsg);
        }
    }
}