package com.epam.jwd.core_final.exception;

import java.util.Arrays;

public class EntityNotFoundException extends Exception{
    private final String entityName;

    public EntityNotFoundException(String entityName) {
        super();
        this.entityName = entityName;
    }

    public EntityNotFoundException(String entityName, Object... args) {
        super();
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "Requested Entity: " + entityName;
    }
}
