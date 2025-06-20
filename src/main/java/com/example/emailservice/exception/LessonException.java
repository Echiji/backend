package com.example.emailservice.exception;

/**
 * Exception personnalisée pour les erreurs liées aux leçons
 */
public class LessonException extends RuntimeException {
    
    public LessonException(String message) {
        super(message);
    }
    
    public LessonException(String message, Throwable cause) {
        super(message, cause);
    }
} 