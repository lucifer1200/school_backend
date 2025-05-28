package com.org.apiservices.controller;

import com.org.apiservices.util.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/encryption")
@RequiredArgsConstructor
public class EncryptionController {

    //private final EncryptionUtils encryptionUtils;
	
	private final EncryptionUtils aes256;
    @PostMapping("/encrypt")
    public ResponseEntity<?> encrypt(@RequestBody String plaintext) throws Exception {
        String encryptedText = aes256.encrypt(plaintext);
        return ResponseEntity.ok(encryptedText);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<?> decrypt(@RequestBody String ciphertext) throws Exception {
        String decryptedText = EncryptionUtils.decrypt(ciphertext);
        return ResponseEntity.ok(decryptedText);
    }
}