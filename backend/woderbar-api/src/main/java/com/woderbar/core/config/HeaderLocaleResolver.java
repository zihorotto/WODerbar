package com.woderbar.core.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import java.util.Locale;

public class HeaderLocaleResolver extends AbstractLocaleResolver {

    private final Locale defaultLocale;

    public HeaderLocaleResolver(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        try {
            var localeHeader = request.getHeader("Locale");
            return new Locale(localeHeader);
        } catch (Exception e) {
            return getDefaultLocale();
        }
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("Locale cannot be changed");
    }

    @Override
    protected Locale getDefaultLocale() {
        return defaultLocale;
    }
}
