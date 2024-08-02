package com.woderbar.core.repository.boundary.mapper.request;

import com.woderbar.core.repository.boundary.mapper.BaseMapper;
import com.woderbar.core.repository.model.AdminEntity;
import com.woderbar.core.repository.model.UserRoleEntity;
import com.woderbar.domain.model.Admin;
import com.woderbar.domain.model.type.PermissionType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.woderbar.domain.model.type.PermissionType.editor;
import static com.woderbar.domain.model.type.WoderbarRoleType.ROLE_ADMIN;

@Mapper(componentModel = "spring")
public interface AdminEntityRequestMapper extends BaseMapper<AdminEntity, Admin> {

    @Mapping(source = "clubName", target = "firstName")
    @Mapping(constant = "Egyesületi Admin", target = "lastName")
    @Mapping(constant = "admin", target = "type")
    @Mapping(target = "userRoles", expression = "java(this.adminRoleSetter(creator))")
    @Mapping(target = "permissions", expression = "java(this.permissionSetter())")
    AdminEntity map(String email, String creator, String clubName);

    @Named("adminRoleSetter")
    default List<UserRoleEntity> adminRoleSetter(String creator) {
        List<UserRoleEntity> roles = new ArrayList<>();
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setCreator(creator);
        userRole.setRole(ROLE_ADMIN);
        roles.add(userRole);
        return roles;
    }

    @Named("permissionSetter")
    default Set<PermissionType> permissionSetter() {
        Set<PermissionType> permissions = new HashSet<>();
        permissions.add(editor);
        return permissions;
    }
}
