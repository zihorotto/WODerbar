package com.woderbar.domain.model;

import com.woderbar.domain.model.base.BaseDomain;
import com.woderbar.domain.model.type.LanguageType;
import com.woderbar.domain.model.type.UserType;
import com.woderbar.domain.model.type.WoderbarRoleType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseDomain {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private Boolean active;
    private LanguageType language;
    private String cognitoStatus;
    private LocalDateTime reminderEmailSent;
    private String imageUrl;
    private List<WoderbarRoleType> roles;
    private UserType userType;

    public List<WoderbarRoleType> getRoles() {
        return roles;
    }

    public void setRoles(List<WoderbarRoleType> roles) {
        this.roles = roles;
    }
}
