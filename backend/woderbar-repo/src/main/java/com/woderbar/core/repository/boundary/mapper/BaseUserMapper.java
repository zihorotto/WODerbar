package com.woderbar.core.repository.boundary.mapper;

import com.woderbar.core.repository.model.UserEntity;
import com.woderbar.core.repository.model.UserRoleEntity;
import com.woderbar.domain.model.User;
import com.woderbar.domain.model.pagination.PageInfo;
import com.woderbar.domain.model.type.WoderbarRoleType;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BaseUserMapper<U extends User, E extends UserEntity> extends BaseMapper<U, E> {
    @Override
    @Mapping(source = "imageUrl", target = "fileKey")
    @Mapping(source = "userRoles", target = "userRoles", qualifiedByName = "mapRoles")
    U map(E entity);

    @Mapping(target = "total", source = "totalElements")
    @Mapping(target = "page", source = "number")
    @Mapping(target = "pageSize", source = "size")
    @Mapping(target = "hasNext", expression = "java(pageInfo.hasNext())")
    @Mapping(target = "hasPrevious", expression = "java(pageInfo.hasPrevious())")
    @Mapping(target = "results", source = "content")
    PageInfo<U> map(Page<E> pageInfo);

    @Named("mapRoles")
    default List<WoderbarRoleType> mapRoles(List<UserRoleEntity> userRoles) {
        return userRoles.stream().map(UserRoleEntity::getRole).toList();
    }

}
