package com.psem.Spring.boot.with.Ollama.exception;

public class ResourceNotFoundException extends RuntimeException {

    String resourceName;
    Long fieldId;


    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Long fieldId){
        super(String.format("%s with ID: %d, not found", resourceName, fieldId));
        this.resourceName = resourceName;
        this.fieldId = fieldId;
    }

}
