package com.polimi.ckb.user.service;


import com.polimi.ckb.user.dto.NewUserDto;
import com.polimi.ckb.user.entity.User;

public interface UserService {
    User getUser(Long userId);
    void addNewUser(NewUserDto msg);
}
