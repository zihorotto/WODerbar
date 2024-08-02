package com.woderbar.domain.gateway;


import com.woderbar.domain.model.Athlete;
import org.springframework.stereotype.Component;

@Component
public interface AthleteGateway {
    Athlete create(Athlete athlete);

}
