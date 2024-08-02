package com.woderbar.core.model.request;

import com.woderbar.domain.type.ErrorType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class AthleteApiRequest {
    private String lastName;
    private String firstName;
    private String email;
    private String imageUrl;
    private String addressLine;
    private String postalCode;
    private String city;
    private String country;
    private String medicalPassDateUtc;
    @NotNull(message = ErrorType.TEAM_ASSIGMENT_BAD_REQUEST_ERROR_KEY)
    @NotEmpty(message = ErrorType.TEAM_ASSIGMENT_BAD_REQUEST_ERROR_KEY)
    private Set<Long> teamAssignments;
    private Boolean active;
    private String language;
}
