package com.woderbar.core.util;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@AllArgsConstructor
@Component
public class MessageResolver {

    private static final String CODE_POST_FIX = ".code";
    private static final String STATUS_POST_FIX = ".status";
    private static final String MESSAGE_POST_FIX = ".message";

    private final MessageSource messageSource;

   // public ErrorApiResponse getErrorApiResponseByMessageKeyPrefix(String messageKeyPrefix) {
  //      return new ErrorApiResponse(
     //           getCodeByCodeKeyPrefix(messageKeyPrefix),
     //           getStatusByStatusKeyPrefix(messageKeyPrefix),
    //            getMessageByMessageKeyPrefix(messageKeyPrefix));
   // }

    public String getCodeByCodeKeyPrefix(String messageKeyPrefix) {
        return getStringByKey(messageKeyPrefix + CODE_POST_FIX, LocaleContextHolder.getLocale());
    }

    public Integer getStatusByStatusKeyPrefix(String messageKeyPrefix) {
        return getIntegerByKey(messageKeyPrefix + STATUS_POST_FIX, LocaleContextHolder.getLocale());
    }

    public String getMessageByMessageKeyPrefix(String messageKeyPrefix) {
        return getStringByKey(messageKeyPrefix + MESSAGE_POST_FIX, LocaleContextHolder.getLocale());
    }

    public String getStringByMessageKeyPrefix(String messageKeyPrefix, Object[] args) {
        return getStringByKey(messageKeyPrefix + MESSAGE_POST_FIX, args, LocaleContextHolder.getLocale());
    }

    private String getStringByKey(String key, Locale locale) {
        return getStringByKey(key, null, locale);
    }

    private String getStringByKey(String key, Object[] args, Locale locale) {
        return messageSource.getMessage(key, args, locale);
    }

    private Integer getIntegerByKey(String key, Locale locale) {
        return Integer.parseInt(messageSource.getMessage(key, null, locale));
    }
}
