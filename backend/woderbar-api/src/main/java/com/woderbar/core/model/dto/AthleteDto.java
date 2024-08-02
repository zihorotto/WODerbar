package com.woderbar.core.model.dto;

import lombok.Data;

@Data
public class AthleteDto {
    private final Long id;
    private final Boolean isActive;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String type;
    private final String imageUrl;
    private final String medicalPassDateUtc;
    private final AddressDto address;
}
