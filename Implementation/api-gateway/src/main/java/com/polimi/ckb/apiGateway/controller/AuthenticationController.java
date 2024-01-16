package com.polimi.ckb.apiGateway.controller;

import com.polimi.ckb.apiGateway.dto.AuthenticationRequest;
import com.polimi.ckb.apiGateway.dto.AuthenticationResponse;
import com.polimi.ckb.apiGateway.dto.RegisterRequest;
import com.polimi.ckb.apiGateway.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.ok(service.register(request));
        }catch (Exception e){
            return null; //TODO
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        System.out.println("received login request");
        return ResponseEntity.ok(service.authenticate(request));
    }


}
