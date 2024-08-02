package com.woderbar.domain.exception;

import com.woderbar.domain.type.ErrorType;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.regex.Matcher;

import static com.woderbar.domain.exception.EscapeUtils.escapeForLog;


@Slf4j
@Getter
public class WoderbarException extends RuntimeException {

    private final ErrorType errorType;

    public WoderbarException(ErrorType errorType, String message, Object... params) {
        this(null, errorType, message, params);
    }

    public WoderbarException(Throwable cause, ErrorType errorType, String message, Object... params) {
        super(getFormattedMessage(message, params), cause);
        if (Arrays.stream(params).anyMatch(p -> p instanceof Throwable)) {
            throw new IllegalArgumentException(
                    "Wrong " + getClass() + " usage! The cause exception should be the first element in the "
                            + getClass() + " constructor, not the last!");
        }
        this.errorType = errorType;
    }

    /**
     * Formats the given message by replacing the placeholders ('{}') with the given
     * parameters in line.
     *
     * @param originalMessage the formattable message
     * @param params          the params to format the message with
     * @return the formatted message
     */
    protected static String getFormattedMessage(final String originalMessage, final Object... params) {
        final String originalPlaceholder = "{}";
        final String nonRegexpPlaceholder = "%%%";
        if (!originalMessage.contains(originalPlaceholder) && params != null && params.length > 0) {
            log.warn(
                    "The exception message has no placeholders but parameters are provided for the message! Message: {}; Parameters: {}",
                    originalMessage, escapeForLog(params));
            return originalMessage;
        }
        if (params == null) {
            log.warn("There is no parameter provided for the message {}. No need to format.", originalMessage);
            return originalMessage;
        }
        String message = originalMessage;
        for (Object p : params) {
            message = message.replace(originalPlaceholder, nonRegexpPlaceholder).replaceFirst(nonRegexpPlaceholder,
                    Matcher.quoteReplacement(String.valueOf(p)));
        }
        if (message.contains(nonRegexpPlaceholder)) {
            log.warn(
                    "The exception message has placeholders which could not be replaced with any other parameters! Original message: {}; Formatted message: {}; Parameters: {}",
                    originalMessage, message, escapeForLog(params));
        }
        return message;
    }

}
