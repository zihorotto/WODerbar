package com.woderbar.core.repository.model;


import com.woderbar.core.repository.model.base.BaseEntityWithFile;
import com.woderbar.domain.model.type.LanguageType;
import com.woderbar.domain.model.type.PermissionType;
import com.woderbar.domain.model.type.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class UserEntity extends BaseEntityWithFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    @Nullable
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String username;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean active;

    @Column
    @Enumerated(EnumType.STRING)
    private LanguageType language;

    @Column(name = "cognito_status")
    private String cognitoStatus;

    @Column(name = "reminder_email_sent")
    private LocalDateTime reminderEmailSent;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<UserRoleEntity> userRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserPushTokenEntity> userPushTokens = new ArrayList<>();

    @ElementCollection(targetClass = PermissionType.class)
    @CollectionTable(name = "j_user_permission",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Set<PermissionType> permissions = new HashSet<>();

    public UserEntity() {
        setActive(true);
        setLanguage(LanguageType.HU);
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    public String getName() {
        if (getLanguage() == LanguageType.HU) {
            return getLastName() + " " + getFirstName();
        } else {
            return getFirstName() + " " + getLastName();
        }
    }

    public List<UserRoleEntity> getUserRoles() {
        if (userRoles == null) {
            userRoles = new ArrayList<>();
        }
        return userRoles;
    }

    public List<UserPushTokenEntity> getUserPushTokens() {
        if (null == userPushTokens) {
            userPushTokens = new ArrayList<>();
        }
        return userPushTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserEntity user)) {
            return false;
        }
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    public UserType getUserType() {
        if (this instanceof AthleteEntity) {
            return UserType.athlete;
        } else if (this instanceof AdminEntity) {
            return UserType.admin;
        } else if (this instanceof TrainerEntity) {
            return UserType.trainer;
        } else {
            throw new UnsupportedOperationException("User type not supported");
        }
    }

    public void setUserRole() {
        this.userRoles.forEach(userRoleEntity -> userRoleEntity.setUser(this));
    }
}
