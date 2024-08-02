package com.woderbar.core.repository.model.base;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
@Cacheable(false)
public abstract class BaseEntityWithFile extends BaseEntity {

    @Column(name = "file_key")
    private String fileKey;

}