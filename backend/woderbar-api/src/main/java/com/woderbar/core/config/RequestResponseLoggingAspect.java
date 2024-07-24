package com.woderbar.core.config;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class RequestResponseLoggingAspect {

    private static final String REQUEST_TEMPLATE = "%s: %s %s %s";
    private static final String RESPONSE_TEMPLATE = "%s: %s %s %s %d";
    private static final String REQUEST = "REQUEST";
    private static final String RESPONSE = "RESPONSE";

    @Resource
    private HttpServletRequest request;
    @Resource
    private HttpServletResponse response;

    @Before(value = "@within(org.springframework.web.bind.annotation.RestController)")
    public void logRequest(JoinPoint joinPoint) {
        String method = request.getMethod();
        String path = request.getRequestURI() + getQueryString(request);
        String languageHeader = request.getLocale().getLanguage();
        log.info(String.format(REQUEST_TEMPLATE, REQUEST, method, path, languageHeader));
    }

    @AfterReturning(value = "@within(org.springframework.web.bind.annotation.RestController)", returning = "returnValue")
    public void logResponse(JoinPoint joinPoint, Object returnValue) throws Throwable {
        int httpStatus = response.getStatus();
        String method = request.getMethod();
        String path = request.getRequestURI() + getQueryString(request);
        String languageHeader = response.getLocale().getLanguage();
        final String logMessage = String.format(RESPONSE_TEMPLATE, RESPONSE, method, path, languageHeader, httpStatus);
        if (httpStatus >= 400) {
            log.error(logMessage);
        } else {
            log.info(logMessage);
        }
    }

    private String getQueryString(HttpServletRequest request) {
        String queryString = request.getQueryString();
        return queryString != null ? "?" + queryString : "";
    }
}
