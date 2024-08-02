package com.woderbar.domain.model;

import com.woderbar.domain.model.type.AdminType;
import com.woderbar.domain.model.type.PermissionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

    private AdminType type;
    private Set<PermissionType> permissions;
}
