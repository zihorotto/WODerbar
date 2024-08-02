package com.woderbar.core.model.dto;


import lombok.Data;

import java.util.List;

@Data
public class AdminDto {

    private final Long id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String type = "admin";
    private final String imageUrl;
    private final List<String> permissions;
    private final Boolean isActive;
}
