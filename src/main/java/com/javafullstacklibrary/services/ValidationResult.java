package com.javafullstacklibrary.services;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult<T> {
    private final boolean success;
    private final T data;
    private final String message;
    private final Map<String, String> fieldErrors;
    
    private ValidationResult(boolean success, T data, String message, Map<String, String> fieldErrors) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.fieldErrors = fieldErrors != null ? fieldErrors : new HashMap<>();
    }
    
    public static <T> ValidationResult<T> success(T data) {
        return new ValidationResult<>(true, data, null, null);
    }
    
    public static <T> ValidationResult<T> failure(String message) {
        return new ValidationResult<>(false, null, message, null);
    }
    
    public static <T> ValidationResult<T> failure(Map<String, String> fieldErrors, String message) {
        return new ValidationResult<>(false, null, message, fieldErrors);
    }
    
    // Getters
    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getMessage() { return message; }
    public Map<String, String> getFieldErrors() { return fieldErrors; }
    public boolean hasFieldErrors() { return !fieldErrors.isEmpty(); }
}