package com.woderbar.core.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.woderbar.core.model.security.UserPrincipal;
import com.woderbar.core.service.ApiValidationService;
import com.woderbar.core.service.CustomUserDetailsService;
import com.woderbar.core.util.CognitoUtil;
import com.woderbar.domain.exception.WoderbarException;
import com.woderbar.domain.type.ErrorType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class TokenFilter extends BaseSecurityFilter {

    private ApiValidationService validationService;
    private CustomUserDetailsService customUserDetailsService;
    private CognitoUtil cognitoUtil;

    @Autowired
    public void setValidationService(ApiValidationService validationService) {
        this.validationService = validationService;
    }

    @Autowired
    public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Autowired
    public void setCognitoUtil(CognitoUtil cognitoUtil) {
        this.cognitoUtil = cognitoUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwtFromRequest(request);
        if (jwt == null) {
            throw new WoderbarException(ErrorType.UNAUTHORIZED_ERROR, "JWT token is null");
        }
        final JWTClaimsSet jwtClaimsSet = validationService.getValidJWTClaimsSet(jwt);

        final String username = cognitoUtil.getUsername(jwtClaimsSet);
        UserPrincipal userDetails = (UserPrincipal) customUserDetailsService.loadUserByUsername(username);
        if (!userDetails.isActive()) {
            throw new WoderbarException(ErrorType.FORBIDDEN_ERROR, "User is not active");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}