package com.woderbar.core.service;


import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInteractor userInteractor;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userInteractor.getUserPrincipalByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

}