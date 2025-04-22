package com.gpoint.files_service.exception;

import java.util.Arrays;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, String parameter, String value) {
        super("Entity with name " + entityName + " and " + parameter + "=" + value + " not found");
    }

    public EntityNotFoundException(String entityName, String parameters, String... value) {
        super("Entity with name " + entityName + " and " + parameters + " not found: " + Arrays.toString(value));
    }
}
