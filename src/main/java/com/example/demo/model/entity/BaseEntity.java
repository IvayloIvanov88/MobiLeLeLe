package com.example.demo.model.entity;

import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;


@ToString
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected Instant created;

    @Column(nullable = false)
    protected Instant updated;

    public Long getId() {
        return id;
    }

    public BaseEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Instant getCreated() {
        return created;
    }

    public BaseEntity setCreated(Instant created) {
        this.created = created;
        return this;
    }

    public Instant getUpdated() {
        return updated;
    }

    public BaseEntity setUpdated(Instant updated) {
        this.updated = updated;
        return this;
    }
}

