package com.gisma.pams.exception;

public class DiseaseNotFoundException extends RuntimeException {
    public DiseaseNotFoundException(String message) {
        super(message);
    }

    public DiseaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
