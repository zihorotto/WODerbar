package com.woderbar.core.config;

import com.woderbar.core.model.exception.ErrorApiResponse;
import com.woderbar.core.util.MessageResolver;
import com.woderbar.domain.exception.WoderbarException;
import com.woderbar.domain.type.ErrorType;
import jakarta.annotation.Resource;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.apache.commons.lang3.StringUtils.defaultString;

@Slf4j
@RestControllerAdvice
public class WoderbarRestControllerAdvice {

    @Resource
    private MessageResolver messageResolver;

    @ExceptionHandler(WoderbarException.class)
    public ResponseEntity<ErrorApiResponse> medalystException(WoderbarException e) {
        var errorApiResponse = messageResolver.getErrorApiResponseByMessageKeyPrefix(e.getErrorType().name());
        log.error("Exception occurred with error type {}: ", e.getErrorType(), e);
        return ResponseEntity.status(errorApiResponse.getStatus()).body(errorApiResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorApiResponse> forbiddenException(AccessDeniedException e) {
        var errorApiResponse = messageResolver.getErrorApiResponseByMessageKeyPrefix(ErrorType.FORBIDDEN_ERROR.name());
        log.error("Authorization Exception occurred with error type {}: ", ErrorType.FORBIDDEN_ERROR, e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorApiResponse);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorApiResponse> defaultException(Throwable e) {
        var errorApiResponse = messageResolver.getErrorApiResponseByMessageKeyPrefix(ErrorType.INTERNAL_SERVER_ERROR.name());
        log.error("Unknown Exception occurred with error type {}: ", ErrorType.INTERNAL_SERVER_ERROR, e);
        return ResponseEntity.internalServerError().body(errorApiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorApiResponse> handleRefundingConstraintViolation(ConstraintViolationException e) {
        return handleValidationException(e, getMessage(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorApiResponse> handleValidationException(MethodArgumentNotValidException e) {
        return handleValidationException(e, getMessage(e));
    }

    private <E extends Exception> ResponseEntity<ErrorApiResponse> handleValidationException(E ex, String errorTypeStr) {
        var errorApiResponse = messageResolver.getErrorApiResponseByMessageKeyPrefix(errorTypeStr);
        log.error("Exception occurred with error type {}: ", errorTypeStr, ex);
        return ResponseEntity.status(errorApiResponse.getStatus()).body(errorApiResponse);
    }

    private String getMessage(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(objectError -> defaultString(objectError.getMessage()))
                .sorted()
                .findFirst()
                .orElse(StringUtils.EMPTY);
    }

    private String getMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream()
                .map(objectError -> defaultString(objectError.getDefaultMessage()))
                .sorted()
                .findFirst()
                .orElse(StringUtils.EMPTY);
    }

}
