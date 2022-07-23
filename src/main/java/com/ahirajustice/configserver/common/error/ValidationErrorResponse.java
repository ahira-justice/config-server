package com.ahirajustice.configserver.common.error;

import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {
    
    private Map<String, String> errors;

    public ValidationErrorResponse() {
        super();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
