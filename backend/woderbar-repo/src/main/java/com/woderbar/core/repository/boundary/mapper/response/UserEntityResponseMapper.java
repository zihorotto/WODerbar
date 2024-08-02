package com.woderbar.core.repository.boundary.mapper.response;


import com.woderbar.core.repository.boundary.mapper.BaseUserMapper;
import com.woderbar.core.repository.model.UserEntity;
import com.woderbar.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityResponseMapper extends BaseUserMapper<User, UserEntity> {

}
