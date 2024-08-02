package com.woderbar.core.repository.model;

import com.woderbar.domain.model.type.WoderbarRoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "trainer")
@PrimaryKeyJoinColumn(name = "id")
public class TrainerEntity extends UserEntity {

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Size(min = 1)
    @ManyToMany
    @JoinTable(name = "j_trainer_athletes",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "athlete_id")
    )
    private Set<AthleteEntity> athletes = new LinkedHashSet<>();

    public TrainerEntity() {
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRole(WoderbarRoleType.ROLE_TRAINER);
        userRole.setUser(this);
        getUserRoles().add(userRole);
    }


}
