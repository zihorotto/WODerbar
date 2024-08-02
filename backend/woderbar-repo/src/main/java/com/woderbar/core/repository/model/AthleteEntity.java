package com.woderbar.core.repository.model;

import com.woderbar.domain.model.type.WoderbarRoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "athlete")
@PrimaryKeyJoinColumn(name = "id")
public class AthleteEntity extends UserEntity {
    @Column(name = "medical_pass")
    private LocalDateTime medicalPass;

    @Column
    private String addressLine;

    @Column
    private String addressPostalCode;

    @Column
    private String addressCity;

    @Column
    private String addressCountry;

    @ManyToMany(mappedBy = "athletes")
    private Set<TrainerEntity> trainers = new LinkedHashSet<>();

    public AthleteEntity() {
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(WoderbarRoleType.ROLE_ATHLETE);
        userRole.setUser(this);
        getUserRoles().add(userRole);
    }
}
