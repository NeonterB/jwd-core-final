package com.epam.jwd.core_final.exception;

import java.util.Arrays;

public class EntityNotFoundException extends Exception{
    private final String entityName;
    private final Object[] args;

    public EntityNotFoundException(String entityName) {
        super();
        this.entityName = entityName;
        args = null;
    }

    public EntityNotFoundException(String entityName, Object... args) {
        super();
        this.entityName = entityName;
        this.args = args;
    }

    @Override
    public String getMessage() {
        return "Entity " + entityName + " with arguments " + Arrays.toString(args) + " not found";
    }
}
