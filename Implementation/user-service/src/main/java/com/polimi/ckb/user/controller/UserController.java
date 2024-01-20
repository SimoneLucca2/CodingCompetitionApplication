package com.polimi.ckb.user.controller;

import com.polimi.ckb.user.dto.GetUserDto;
import com.polimi.ckb.user.dto.TournamentsForUserDto;
import com.polimi.ckb.user.dto.UsernameDto;
import com.polimi.ckb.user.entity.Student;
import com.polimi.ckb.user.entity.Tournament;
import com.polimi.ckb.user.entity.User;
import com.polimi.ckb.user.repository.StudentRepository;
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
    private final StudentRepository studentRepository;

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

    @GetMapping("/student/tournaments/{userId}")
    public ResponseEntity<TournamentsForUserDto> getTournaments(@PathVariable Long userId) {
        try{
            Student student = studentRepository.findById(userId).orElseThrow(() -> new RuntimeException("Student not found"));

            return ResponseEntity.ok(
                    TournamentsForUserDto.builder().tournaments(student.getTournaments()).build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
