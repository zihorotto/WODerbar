package com.woderbar.core.repository.model;


import com.woderbar.core.repository.model.base.BaseEntity;
import com.woderbar.domain.model.type.ClientPlatformType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_push_token")
public class UserPushTokenEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(nullable = false)
    private String token;

    @Column()
    @Enumerated(EnumType.STRING)
    private ClientPlatformType platform;

    public UserPushTokenEntity(String token) {
        this.token = token;
    }

    public UserPushTokenEntity(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }

    public UserPushTokenEntity(String token, ClientPlatformType platform, UserEntity user) {
        this.token = token;
        this.platform = platform;
        this.user = user;
    }

}
