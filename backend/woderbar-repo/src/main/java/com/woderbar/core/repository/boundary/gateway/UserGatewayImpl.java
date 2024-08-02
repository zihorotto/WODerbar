package com.woderbar.core.repository.boundary.gateway;

import com.woderbar.core.repository.UserRepository;
import com.woderbar.core.repository.boundary.mapper.response.UserEntityResponseMapper;
import com.woderbar.domain.gateway.UserGateway;
import com.woderbar.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@AllArgsConstructor
@Service
public class UserGatewayImpl implements UserGateway {

    private final UserRepository userRepository;
    private final UserEntityResponseMapper responseMapper;

    @Override
    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUsername(username).map(responseMapper::map);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
