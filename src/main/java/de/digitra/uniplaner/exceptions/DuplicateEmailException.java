package de.digitra.uniplaner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateEmailException extends Exception  {
    public DuplicateEmailException(String message) {
        super(message);
    }
}

