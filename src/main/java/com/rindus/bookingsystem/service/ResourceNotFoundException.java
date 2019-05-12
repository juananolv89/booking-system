package com.rindus.bookingsystem.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** Exception thrown in case that the required resource is not found */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /** generated serial number */
    private static final long serialVersionUID = -4286078190864683220L;

    public ResourceNotFoundException() {
	super();
    }

    public ResourceNotFoundException(String message, Throwable cause) {
	super(message, cause);
    }

    public ResourceNotFoundException(String message) {
	super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
	super(cause);
    }
}