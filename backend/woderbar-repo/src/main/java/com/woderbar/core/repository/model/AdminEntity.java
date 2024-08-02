package com.woderbar.core.repository.model;

import com.woderbar.domain.model.type.AdminType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "admins")
@OnDelete(action = OnDeleteAction.CASCADE)
public class AdminEntity extends UserEntity {

    @Enumerated(EnumType.STRING)
    @Column
    private AdminType type;

    @Override
    public String getName() {
        return getLastName() + " " + getFirstName();
    }

}
