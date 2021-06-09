package com.example.api.exceptions;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Standard notFound(EntityNotFoundException ex, HttpServletRequest http) {
        Standard error = new Standard();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError(HttpStatus.NOT_FOUND.name());
        error.setMessage(ex.getMessage());
        error.setPath(http.getRequestURI());

        return error;
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Standard unsupported(UnsupportedOperationException ex, HttpServletRequest http) {
        Standard error = new Standard();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError(HttpStatus.NOT_FOUND.name());
        error.setMessage(ex.getMessage());
        error.setPath(http.getRequestURI());

        return error;
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public Standard exists(EntityExistsException ex, HttpServletRequest http) {
        Standard error = new Standard();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError(HttpStatus.CONFLICT.name());
        error.setMessage(ex.getMessage());
        error.setPath(http.getRequestURI());

        return error;
    }

    @ExceptionHandler(JpaSystemException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public Standard constraintViolation(JpaSystemException ex, HttpServletRequest http){
        Standard  standard = new Standard();
        
        standard.setTimestamp(Instant.now());
        standard.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        standard.setError(HttpStatus.UNPROCESSABLE_ENTITY.name());
        standard.setMessage(ex.getMessage());
        standard.setPath(http.getRequestURI());

        return standard; 
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public Standard handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest http) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Standard error = new Standard();

        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError(HttpStatus.NOT_FOUND.name());
        error.setMessage(errors.toString());
        error.setPath(http.getRequestURI());

        return error;
    }
}
