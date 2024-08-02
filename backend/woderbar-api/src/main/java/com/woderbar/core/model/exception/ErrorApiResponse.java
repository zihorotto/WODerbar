package com.woderbar.core.model.exception;

import lombok.Data;

@Data
public final class ErrorApiResponse {

    private final String code;
    private final Integer status;
    private final String message;
}
