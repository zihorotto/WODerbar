package com.woderbar.domain.gateway;


import com.woderbar.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserGateway {

    Optional<User> getUserByUserName(String username);

    boolean existsByEmail(String email);
}
