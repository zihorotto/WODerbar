package com.woderbar.core.repository.boundary.mapper.request;


import com.woderbar.core.repository.boundary.mapper.BaseMapper;
import com.woderbar.core.repository.model.AthleteEntity;
import com.woderbar.core.repository.model.UserRoleEntity;
import com.woderbar.domain.model.Athlete;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

import static com.woderbar.domain.model.type.WoderbarRoleType.ROLE_ATHLETE;


@Mapper(componentModel = "spring")
public interface AthleteEntityRequestMapper extends BaseMapper<AthleteEntity, Athlete> {

    @Override
    @Mapping(target = "userRoles", expression = "java(this.athleteRoleSetter(athlete.getCreator()))")
    @Mapping(source = "imageUrl", target = "fileKey")
    @Mapping(target = "permissions", ignore = true)
    AthleteEntity map(Athlete athlete);

    @Named("athleteRoleSetter")
    default List<UserRoleEntity> athleteRoleSetter(String creator) {
        List<UserRoleEntity> roles = new ArrayList<>();
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setCreator(creator);
        userRole.setRole(ROLE_ATHLETE);
        roles.add(userRole);
        return roles;
    }
}
