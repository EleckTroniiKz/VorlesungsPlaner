package de.digitra.uniplaner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;
import java.util.UUID;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidTransactionException extends RuntimeException {

    private static final String MESSAGE = "Transaction is not allowed, balance will be %s for transaction %s";
    private static final String MESSAGE_TRANSACTION_EXIST = "Transaction %s already created";

    public InvalidTransactionException(BigDecimal balance, UUID reference) {
        super(String.format(MESSAGE, balance.toString(), reference.toString()));
    }

    public InvalidTransactionException(UUID reference) {
        super(String.format(MESSAGE_TRANSACTION_EXIST, reference.toString()));
    }
}
