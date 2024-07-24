package com.woderbar.core.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;


@AllArgsConstructor
abstract class BaseSecurityFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().equals("/woderbar")
                || request.getRequestURI().equals("/woderbar/")
                || request.getRequestURI().matches("^\\/\\w*\\/*swagger-ui\\/.*")
                || request.getRequestURI().matches("^\\/\\w*\\/*v3/api-docs/swagger-config")
                || request.getRequestURI().matches("^\\/\\w*\\/*apidoc/woderbar.yaml")
                || request.getRequestURI().matches("^\\/\\w*\\/*api\\/system\\/health");
    }

}
