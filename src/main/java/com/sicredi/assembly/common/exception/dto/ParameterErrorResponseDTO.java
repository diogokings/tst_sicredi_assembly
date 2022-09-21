package com.sicredi.assembly.common.exception.dto;

import java.util.HashMap;
import java.util.Map;

public class ParameterErrorResponseDTO extends StandardErrorResponseDTO {

    private static final long serialVersionUID = 1L;

    private Map<String, String> errors;

    public ParameterErrorResponseDTO(String message) {
        super(message);
        this.errors = new HashMap();
    }

    public ParameterErrorResponseDTO(String message, Map errors) {
        super(message);
        this.errors = errors;
    }

    public void addError(String param, String message) {
        this.errors.put(param, message);
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
