package com.woderbar.core.boundary.mapper.response;


import com.woderbar.core.model.dto.AthleteDto;
import com.woderbar.core.model.response.pagination.PaginatedApiResponse;
import com.woderbar.core.repository.boundary.mapper.BaseMapper;
import com.woderbar.domain.model.Athlete;
import com.woderbar.domain.model.pagination.PageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AthleteApiResponseMapper extends BaseMapper<AthleteDto, Athlete> {

    @Override
    @Mapping(source = "addressCountry", target = "address.country")
    @Mapping(source = "addressPostalCode", target = "address.postalCode")
    @Mapping(source = "addressCity", target = "address.city")
    @Mapping(source = "addressLine", target = "address.street")
    @Mapping(source = "userType", target = "type")
    @Mapping(source = "active", target = "isActive")
    AthleteDto map(Athlete athlete);

    PaginatedApiResponse<AthleteDto> map(PageInfo<Athlete> all);
}
