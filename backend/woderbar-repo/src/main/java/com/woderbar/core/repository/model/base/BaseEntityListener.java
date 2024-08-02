package com.woderbar.core.repository.model.base;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;
import java.time.LocalDateTime;


public class BaseEntityListener { // TODO

    @PrePersist
    public void prePersist(BaseEntity entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            entity.setCreator(authentication.getName());
        } else {
            entity.setCreator("anonymous");
        }
        entity.setCreated(null == entity.getCreated() ? LocalDateTime.from(Instant.now()) : entity.getCreated());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            entity.setUpdater(authentication.getName());
        } else {
            entity.setUpdater("anonymus");
        }
        entity.setUpdated(LocalDateTime.from(Instant.now()));
    }

}
