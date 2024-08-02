package com.woderbar.core.boundary.mapper;

import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isBlank;

public interface BaseMapper<TO, FROM> {

    List<TO> map(List<FROM> froms);

    TO map(FROM from);

    @Named("mapStringToLocalDateTime")
    default LocalDateTime mapStringToLocalDateTime(String value) {
        return isBlank(value) ? null : LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    }

    @Named("mapLocalDateTimeToString")
    default String mapLocalDateTimeToString(LocalDateTime value) {
        return value == null ? null : DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS").format(value);
    }
}
