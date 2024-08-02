package com.woderbar.core.boundary.mapper.response;


import com.woderbar.core.model.dto.AdminDto;
import com.woderbar.core.repository.boundary.mapper.BaseMapper;
import com.woderbar.domain.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminApiResponseMapper extends BaseMapper<AdminDto, Admin> {

    @Override
    @Mapping(target = "type", ignore = true)
    @Mapping(source = "active", target = "isActive")
    AdminDto map(Admin admin);
}
