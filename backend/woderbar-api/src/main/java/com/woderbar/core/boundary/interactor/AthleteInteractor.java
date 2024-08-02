package com.woderbar.core.boundary.interactor;


import com.woderbar.core.boundary.mapper.request.AthleteRequestMapper;
import com.woderbar.core.boundary.mapper.response.AthleteApiResponseMapper;
import com.woderbar.core.model.dto.AthleteDto;
import com.woderbar.core.model.request.AthleteApiRequest;
import com.woderbar.domain.service.AthleteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AthleteInteractor {

    private final AthleteService athleteService;
    private final AthleteApiResponseMapper responseMapper;
    private final AthleteRequestMapper athleteRequestMapper;

    public AthleteDto create(AthleteApiRequest request) {
        return responseMapper.map(athleteService.create(athleteRequestMapper.map(request)));
    }
}
