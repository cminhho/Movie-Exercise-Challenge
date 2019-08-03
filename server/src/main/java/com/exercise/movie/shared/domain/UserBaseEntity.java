package com.exercise.movie.shared.domain;

import com.exercise.movie.shared.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class UserBaseEntity<U> extends BaseEntity<U> {

    @Column(name = "deleted")
    private boolean deleted;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public abstract Long getId();
}