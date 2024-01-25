package com.polimi.ckb.apiGateway.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.apiGateway.dto.AuthenticationRequest;
import com.polimi.ckb.apiGateway.dto.AuthenticationResponse;
import com.polimi.ckb.apiGateway.dto.NewUserDto;
import com.polimi.ckb.apiGateway.dto.RegisterRequest;
import com.polimi.ckb.apiGateway.entity.User;
import com.polimi.ckb.apiGateway.exception.UserNotFoundException;
import com.polimi.ckb.apiGateway.exception.WrongPasswordException;
import com.polimi.ckb.apiGateway.repository.UserRepository;
import com.polimi.ckb.apiGateway.service.kafka.NewUserKafkaProducer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final NewUserKafkaProducer newUserKafkaProducer;

    public AuthenticationResponse register(@Valid RegisterRequest request) throws JsonProcessingException {
        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .surname(request.getSurname())
                .nickname(request.getNickname())
                .type(request.getType())
                .build();

        User newUser = repository.save(user);

        NewUserDto newUserDto = NewUserDto.buildFromUser(newUser);

        newUserKafkaProducer.sendNewUser(newUserDto);

        return AuthenticationResponse.builder()
                .userType(newUser.getType())
                .nickname(newUser.getNickname())
                .userId(newUser.getUserId())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(request.getEmail()));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new WrongPasswordException(request.getPassword());
        }

        return AuthenticationResponse.builder()
                .userType(user.getType())
                .nickname(user.getNickname())
                .userId(user.getUserId())
                .build();
    }

}
