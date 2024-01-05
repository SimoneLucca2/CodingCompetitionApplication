package com.polimi.ckb.user.repository;

import com.polimi.ckb.user.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @NotNull Optional<User> findById(@NotNull Long id);
}
