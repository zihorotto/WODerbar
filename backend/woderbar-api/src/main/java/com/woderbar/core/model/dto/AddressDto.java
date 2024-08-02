package com.woderbar.core.model.dto;

import lombok.Data;

@Data
public class AddressDto {
    private final String country;
    private final String postalCode;
    private final String city;
    private final String street;
}
