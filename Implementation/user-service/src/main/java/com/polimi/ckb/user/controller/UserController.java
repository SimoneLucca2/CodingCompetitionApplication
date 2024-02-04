package com.polimi.ckb.user.controller;

import com.polimi.ckb.user.dto.GetUserDto;
import com.polimi.ckb.user.entity.User;
import com.polimi.ckb.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<User> getUser(@Valid @RequestBody GetUserDto msg) {
        try{
            log.info("Getting user with message: {}", msg);
            return ResponseEntity.ok(userService.getUser(msg.getUserId()));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        try{
            log.info("Getting user with id: {}", userId);
            return ResponseEntity.ok(userService.getUser(userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/name/{userId}")
    public ResponseEntity<String> getUsername(@PathVariable Long userId) {
        try{
            log.info("Getting username with id: {}", userId);
            return ResponseEntity.ok(
                    userService.getUser(userId).getName()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/email/{userId}")
    public ResponseEntity<String> getEmail(@PathVariable Long userId) {
        try{
            log.info("Getting username with id: {}", userId);
            return ResponseEntity.ok(
                    userService.getUser(userId).getEmail()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
