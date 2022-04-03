package de.digitra.uniplaner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Account not found for account iban %s";

    public AccountNotFoundException(String accountIban) {
        super(String.format(MESSAGE, accountIban));
    }
}
