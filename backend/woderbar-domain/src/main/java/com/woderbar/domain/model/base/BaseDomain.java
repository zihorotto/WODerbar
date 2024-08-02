package com.woderbar.domain.model.base;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BaseDomain {

    private LocalDateTime created;
    private String creator;
    private LocalDateTime updated;
    private String updater;
    private Long version;

}
