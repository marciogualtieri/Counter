package com.synchronoss.counter.exceptions;

import com.synchronoss.counter.messages.ExceptionMessages;

public class IllegalOperationException extends RuntimeException {
    public IllegalOperationException(String input) {
        super(buildMessageForOperation(input));
    }

    public IllegalOperationException() {
        super(buildMessageForOperation(ExceptionMessages.MISSING_OPERATION_MESSAGE));
    }

    private static String buildMessageForOperation(String operation) {
        return String.format(ExceptionMessages.INVALID_OPERATION_MESSAGE_FORMAT, operation);
    }
}