package com.sicredi.assembly.common.exception;

import com.sicredi.assembly.common.exception.dto.ParameterErrorResponseDTO;
import com.sicredi.assembly.common.exception.dto.StandardErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    private static final String FIELD_ERROR = "ERROR: Invalid request! REASON: There are missing fields or with invalid values";

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardErrorResponseDTO> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardErrorResponseDTO err = new StandardErrorResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardErrorResponseDTO> constraintViolation(DataIntegrityException e, HttpServletRequest request) {
        StandardErrorResponseDTO err = new StandardErrorResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardErrorResponseDTO> invalidArgument(IllegalArgumentException e, HttpServletRequest request) {
        StandardErrorResponseDTO err = new StandardErrorResponseDTO(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorResponseDTO> fieldValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ParameterErrorResponseDTO requestError = new ParameterErrorResponseDTO(FIELD_ERROR);

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            requestError.getErrors().put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(requestError);
    }

}
