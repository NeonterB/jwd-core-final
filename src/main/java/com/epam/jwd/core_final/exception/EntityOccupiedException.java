package com.epam.jwd.core_final.exception;

public class EntityOccupiedException extends Exception{
    private String entityName;
    private final String message;

    public EntityOccupiedException(String message) {
        this.message = message;
    }

    public EntityOccupiedException(String entityName, String msg) {
        super();
        this.entityName = entityName;
        this.message = msg;
    }

    @Override
    public String getMessage() {
        return message + "; Entity name=" + entityName;
    }
}
