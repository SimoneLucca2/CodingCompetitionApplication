package com.polimi.ckb.user.controller;

import com.polimi.ckb.user.dto.GetUserDto;
import com.polimi.ckb.user.entity.User;
import com.polimi.ckb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(@Valid @RequestBody GetUserDto msg) {
        return ResponseEntity.ok(userService.getUser(msg.getUserId()));
    }
}
