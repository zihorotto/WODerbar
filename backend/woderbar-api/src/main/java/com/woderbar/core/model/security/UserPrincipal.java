package com.woderbar.core.model.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Data
public class UserPrincipal implements UserDetails {

    private final Long id;
    private final String email;
    private final String username;
    private final boolean active;
    private final Collection<GrantedAuthority> authorities;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    /*
    public WoderbarRoleType getAuthorityAsRole() {
        return getAuthorities().stream()
                .map(authority -> toRoleType(authority.getAuthority()))
                .filter(role -> role != WoderbarRoleType.ROLE_USER)
                .findFirst()
                .orElse(null);
    }

    private WoderbarRoleType toRoleType(String authority) {
        try {
            return WoderbarRoleType.valueOf(authority);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

     */
}
