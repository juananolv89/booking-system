package com.rindus.bookingsystem.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown in case an action cannot be performed to a specific booking
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongActionException extends RuntimeException {

    /** generated serial number */
    private static final long serialVersionUID = 586228514187577252L;

    public WrongActionException() {
	super();
    }

    public WrongActionException(String message, Throwable cause) {
	super(message, cause);
    }

    public WrongActionException(String message) {
	super(message);
    }

    public WrongActionException(Throwable cause) {
	super(cause);
    }
}