package com.epam.jwd.core_final.exception;

import com.epam.jwd.core_final.domain.BaseEntity;

public class EntityExistsException extends Exception {
    private final BaseEntity entity;

    public EntityExistsException(BaseEntity entity) {
        super();
        this.entity = entity;
    }

    @Override
    public String getMessage() {
        return entity + " already exists";
    }
}
