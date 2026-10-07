package com.example.grillo.finanzas_personal.common.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message, Object id) {

        super(message + " con id: "+ id + " no encontrado");
    }
}
