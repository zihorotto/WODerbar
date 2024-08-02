package com.woderbar.core.boundary.mapper;

import com.woderbar.core.model.security.UserPrincipal;
import com.woderbar.domain.model.User;
import com.woderbar.domain.model.type.WoderbarRoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface UserPrincipalMapper {

    default Optional<UserPrincipal> mapOptional(Optional<User> user) {
        return user.map(this::map);
    }

    @Mapping(target = "authorities", source = "roles", qualifiedByName = "mapAuthorities")
    UserPrincipal map(User user);

    @Named("mapAuthorities")
    default Collection<GrantedAuthority> mapAuthorities(List<WoderbarRoleType> roles) {
        List<SimpleGrantedAuthority> userRoles = roles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.name()))
                .collect(Collectors.toList());
        userRoles.add(new SimpleGrantedAuthority(WoderbarRoleType.ROLE_USER.name()));
        return Collections.unmodifiableList(userRoles);
    }

}