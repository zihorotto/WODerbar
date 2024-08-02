package com.woderbar.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class Athlete extends User {
    private String creator = "anonymousUser";
    private String addressLine;
    private String addressPostalCode;
    private String addressCity;
    private String addressCountry;
}
