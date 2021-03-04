package com.epam.jwd.core_final.domain;

import java.util.Objects;

public class EntityWrap<T extends BaseEntity> implements BaseEntity {
    private final T entity;
    private boolean isValid = true;

    public EntityWrap(T entity) {
        this.entity = entity;
    }

    @Override
    public Long getId() {
        return entity.getId();
    }

    @Override
    public String getName() {
        return entity.getName();
    }

    public T getEntity() {
        return entity;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityWrap<?> that = (EntityWrap<?>) o;
        return entity.equals(that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity);
    }
}
