package com.woderbar.core.boundary.interactor;

import com.woderbar.core.boundary.mapper.UserPrincipalMapper;
import com.woderbar.core.model.security.UserPrincipal;
import com.woderbar.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserInteractor {
    private final UserService userService;
    private final UserPrincipalMapper userPrincipalMapper;

    @Autowired
    public UserInteractor(@Lazy UserService userService, UserPrincipalMapper userPrincipalMapper) {
        this.userService = userService;
        this.userPrincipalMapper = userPrincipalMapper;
    }

    public Optional<UserPrincipal> getUserPrincipalByUsername(String username) {
        return userPrincipalMapper.mapOptional(userService.getUserByUserName(username));
    }
}