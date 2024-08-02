package com.woderbar.domain.service.impl;


import com.woderbar.domain.gateway.AthleteGateway;
import com.woderbar.domain.model.Athlete;
import com.woderbar.domain.service.AthleteService;
import com.woderbar.domain.service.ValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AthleteServiceImpl implements AthleteService {

    private final AthleteGateway athleteGateway;
    private final ValidationService validationService;


    @Override
    public Athlete create(Athlete athlete) {
        validationService.validateEmailDoesNotExist(athlete.getEmail());
        return athleteGateway.create(athlete);
    }

}
