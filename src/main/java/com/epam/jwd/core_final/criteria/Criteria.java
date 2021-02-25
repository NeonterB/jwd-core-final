package com.epam.jwd.core_final.criteria;

import com.epam.jwd.core_final.domain.BaseEntity;

/**
 * Should be a builder for {@link BaseEntity} fields
 */
public abstract class Criteria<T extends BaseEntity> {
    protected Long id;
    protected String name;

    protected Criteria() {
    }

    public abstract class Builder {
        protected Builder() {
        }

        public Builder setId(Long id) {
            Criteria.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            Criteria.this.name = name;
            return this;
        }

        public abstract Criteria<T> build();
    }
}
