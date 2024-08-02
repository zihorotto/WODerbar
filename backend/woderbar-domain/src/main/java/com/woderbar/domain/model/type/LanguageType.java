package com.woderbar.domain.model.type;


public enum LanguageType {

    HU("Magyar", "hu-HU"),
    EN("Angol", "en-US"),
    DE("Német", "de-DE");

    private final String name;
    private final String locale;

    LanguageType(String name, String locale) {
        this.name = name;
        this.locale = locale;
    }

    public String getCode() {
        return name();
    }

    public final String getName() {
        return this.name;
    }

    public String getLocale() {
        return locale;
    }
}
