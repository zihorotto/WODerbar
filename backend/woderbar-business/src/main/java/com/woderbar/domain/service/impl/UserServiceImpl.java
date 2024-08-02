package com.woderbar.domain.service.impl;

import com.woderbar.domain.gateway.UserGateway;
import com.woderbar.domain.model.User;
import com.woderbar.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserGateway userGateway;

    @Autowired
    public UserServiceImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        return userGateway.getUserByUserName(username);
    }

}