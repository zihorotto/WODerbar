package com.woderbar.domain.service;


import com.woderbar.domain.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUserName(String username);
}