package com.polimi.ckb.apiGateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.apiGateway.dto.AuthenticationRequest;
import com.polimi.ckb.apiGateway.dto.AuthenticationResponse;
import com.polimi.ckb.apiGateway.dto.RegisterRequest;
import com.polimi.ckb.apiGateway.entity.User;
import com.polimi.ckb.apiGateway.repository.UserRepository;
import com.polimi.ckb.apiGateway.service.kafka.NewUserKafkaProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final NewUserKafkaProducer newUserKafkaProducer;

    public AuthenticationResponse register(@Valid RegisterRequest request) throws JsonProcessingException {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .surname(request.getSurname())
                .nickname(request.getNickname())
                .accountType(request.getAccountType())
                .build();

        newUserKafkaProducer.sendNewUser(user);

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
