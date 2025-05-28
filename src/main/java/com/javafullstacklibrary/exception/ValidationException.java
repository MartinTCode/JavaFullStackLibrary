package com.javafullstacklibrary.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
    private final Map<String, String> fieldErrors;
    
    public ValidationException(String message) {
        super(message);
        this.fieldErrors = new HashMap<>();
    }
    
    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }
    
    public ValidationException(String field, String error) {
        super("Validation failed");
        this.fieldErrors = new HashMap<>();
        this.fieldErrors.put(field, error);
    }
    
    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
    
    public void addFieldError(String field, String error) {
        this.fieldErrors.put(field, error);
    }
    
    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }
}