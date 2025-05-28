package com.org.apiservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.apiservices.dto.CommonResponse;
import com.org.apiservices.dto.LoginReq;
import com.org.apiservices.dto.LoginResponse;
import com.org.apiservices.dto.RegistrationReq;
import com.org.apiservices.service.LoginService;
import com.org.apiservices.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/Public")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final EncryptionUtils encryptionUtils;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/Login")
    public ResponseEntity<?> Login(@Validated @RequestBody String encryptedLoginReq) throws Exception {
        String decryptedLoginReq = EncryptionUtils.decrypt(encryptedLoginReq);
        LoginReq loginReq = objectMapper.readValue(decryptedLoginReq, LoginReq.class);
        LoginResponse loginDetails = loginService.validateLogin(loginReq);
        String encryptedLoginDetails = encryptionUtils.encrypt(objectMapper.writeValueAsString(loginDetails));
        return ResponseEntity.ok(encryptedLoginDetails);
    }

    @PostMapping("/Register")
    public ResponseEntity<?> Registration(@Validated @RequestBody String encryptedRegReq) throws Exception {
        String decryptedRegReq = EncryptionUtils.decrypt(encryptedRegReq);
        RegistrationReq regReq = objectMapper.readValue(decryptedRegReq, RegistrationReq.class);
        CommonResponse regResp = loginService.register(regReq);
        String encryptedRegResp = encryptionUtils.encrypt(objectMapper.writeValueAsString(regResp));
        return ResponseEntity.ok(encryptedRegResp);
    }
}