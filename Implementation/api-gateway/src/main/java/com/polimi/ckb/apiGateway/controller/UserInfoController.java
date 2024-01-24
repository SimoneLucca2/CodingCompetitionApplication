package com.polimi.ckb.apiGateway.controller;

import com.polimi.ckb.apiGateway.dto.GetUserIdDto;
import com.polimi.ckb.apiGateway.entity.User;
import com.polimi.ckb.apiGateway.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserRepository userRepository;

    @GetMapping("/getId/{mail}")
    public ResponseEntity<?> getIdFromMail(@PathVariable String mail) {

        try {
            User user = userRepository.findByEmail(mail).orElseThrow(() -> new RuntimeException("User not found"));
            return ResponseEntity.ok(new GetUserIdDto(user.getUserId()));
        }catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
