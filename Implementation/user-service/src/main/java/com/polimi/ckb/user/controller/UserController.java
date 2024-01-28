package com.polimi.ckb.user.controller;

import com.polimi.ckb.user.dto.GetUserDto;
import com.polimi.ckb.user.dto.UsernameDto;
import com.polimi.ckb.user.entity.User;
import com.polimi.ckb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(@Valid @RequestBody GetUserDto msg) {
        try{
            return ResponseEntity.ok(userService.getUser(msg.getUserId()));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(userService.getUser(userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/name/{userId}")
    public ResponseEntity<UsernameDto> getUsername(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(
                    UsernameDto.builder().username(userService.getUser(userId).getName()).build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
