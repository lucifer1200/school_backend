package com.org.apiservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.apiservices.dto.MasterReq;
import com.org.apiservices.dto.MasterResponse;
import com.org.apiservices.service.CommonMasterService;
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
public class CommonMaster {

    private final CommonMasterService commonMasterService;
    private final EncryptionUtils encryptionUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/MasterData")
    public ResponseEntity<?> MasterData(@Validated @RequestBody String encryptedMasterReq) throws Exception {
        // Decrypt the incoming request
        String decryptedMasterReq = EncryptionUtils.decrypt(encryptedMasterReq);
        MasterReq masterReq = objectMapper.readValue(decryptedMasterReq, MasterReq.class);

        // Process the request
        MasterResponse commonMasterData = commonMasterService.getMasterData(masterReq);

        // Encrypt the response
        String encryptedCommonMasterData = encryptionUtils.encrypt(objectMapper.writeValueAsString(commonMasterData));
        return ResponseEntity.ok(encryptedCommonMasterData);
    }
}