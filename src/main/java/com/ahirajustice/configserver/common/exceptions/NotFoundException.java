package com.ahirajustice.configserver.common.exceptions;

public class NotFoundException extends ApplicationDomainException {

    public NotFoundException(String message) {
        super(message, "NotFound", 404);
    }

}
