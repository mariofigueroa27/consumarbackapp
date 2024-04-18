package com.fabrica.hutchisonspring.web.rest.errors;

import java.io.Serializable;

public class FieldExceptionVM implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String objectName;
    private final String field;
    private final String message;

    public FieldExceptionVM(String objectName, String field, String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
