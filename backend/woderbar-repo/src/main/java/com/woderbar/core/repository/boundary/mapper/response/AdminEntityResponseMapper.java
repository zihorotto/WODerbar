package com.woderbar.core.repository.boundary.mapper.response;


import com.woderbar.core.repository.boundary.mapper.BaseMapper;
import com.woderbar.core.repository.model.AdminEntity;
import com.woderbar.domain.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdminEntityResponseMapper extends BaseMapper<Admin, AdminEntity> {

    @Override
    @Mapping(source = "fileKey", target = "imageUrl")
    Admin map(AdminEntity adminEntity);

}
