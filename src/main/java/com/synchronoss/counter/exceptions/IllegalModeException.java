package com.synchronoss.counter.exceptions;

import com.synchronoss.counter.messages.ExceptionMessages;

public class IllegalModeException extends RuntimeException {
    public IllegalModeException(String input) {
        super(buildMessageForMode(input));
    }

    public IllegalModeException() {
        super(ExceptionMessages.MISSING_MODE_MESSAGE);
    }

    private static String buildMessageForMode(String mode) {
        return String.format(ExceptionMessages.INVALID_MODE_MESSAGE_FORMAT, mode);
    }
}
