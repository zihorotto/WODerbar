package com.woderbar.core.repository.boundary.mapper.response;


import com.woderbar.core.repository.boundary.mapper.BaseUserMapper;
import com.woderbar.core.repository.model.AthleteEntity;
import com.woderbar.domain.model.Athlete;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AthleteEntityResponseMapper extends BaseUserMapper<Athlete, AthleteEntity> {

    @Override
    @Mapping(target = "userType", expression = "java(athleteEntity.getUserType())")
    @Mapping(source = "fileKey", target = "imageUrl")
    Athlete map(AthleteEntity athleteEntity);
}
