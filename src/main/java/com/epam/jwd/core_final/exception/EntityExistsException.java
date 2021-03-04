package com.epam.jwd.core_final.exception;

public class EntityExistsException extends Exception {
    private final String entityName;
    private final String message;

    public EntityExistsException(String entityName, String msg) {
        super();
        this.entityName = entityName;
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message + "; Entity name=" + entityName;
    }
}
