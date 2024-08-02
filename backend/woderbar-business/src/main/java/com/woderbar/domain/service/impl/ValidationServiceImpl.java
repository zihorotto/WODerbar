package com.woderbar.domain.service.impl;

import com.woderbar.domain.exception.WoderbarException;
import com.woderbar.domain.gateway.UserGateway;
import com.woderbar.domain.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.woderbar.domain.type.ErrorType.EMAIL_TAKEN_ERROR;


@AllArgsConstructor
@Service
public class ValidationServiceImpl implements ValidationService {

    private final UserGateway userGateway;


    @Override
    public void validateEmailDoesNotExist(String email) {
        if (userGateway.existsByEmail(email)) {
            throw new WoderbarException(EMAIL_TAKEN_ERROR, "Email: {} already taken", email);
        }
    }

}
