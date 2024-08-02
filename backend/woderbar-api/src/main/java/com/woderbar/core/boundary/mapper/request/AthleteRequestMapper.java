package com.woderbar.core.boundary.mapper.request;


import com.woderbar.core.model.request.AthleteApiRequest;
import com.woderbar.core.repository.boundary.mapper.BaseMapper;
import com.woderbar.domain.model.Athlete;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AthleteRequestMapper extends BaseMapper<Athlete, AthleteApiRequest> {

    @Override
    @Mapping(source = "postalCode", target = "addressPostalCode")
    @Mapping(source = "city", target = "addressCity")
    @Mapping(source = "country", target = "addressCountry")
    Athlete map(AthleteApiRequest athleteApiRequest);
}
