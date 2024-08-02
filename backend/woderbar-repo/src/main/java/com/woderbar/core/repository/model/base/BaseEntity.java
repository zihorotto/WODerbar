package com.woderbar.core.repository.model.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@Cacheable(false)
@EntityListeners({BaseEntityListener.class})
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name = "created", columnDefinition = "TIMESTAMP", nullable = false, updatable = false)
    protected LocalDateTime created;
    @Column(name = "creator", nullable = false)
    private String creator;
    @Column(name = "updated")
    private LocalDateTime updated;
    @Column(name = "updater")
    private String updater;
    @Version
    @Column(name = "version")
    private Long version;

}
