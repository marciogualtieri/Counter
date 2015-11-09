package com.synchronoss.counter.test.messages;

import com.synchronoss.counter.messages.ExceptionMessages;
import com.synchronoss.counter.test.constants.TestConstants;

public class TestMessages {

    public static final String INVALID_OPERATION_EXCEPTION_MESSAGE =
            String.format(ExceptionMessages.INVALID_OPERATION_MESSAGE_FORMAT,
                    TestConstants.INVALID_OPERATION_INPUT_STRING);
    public static final String INVALID_MODE_EXCEPTION_MESSAGE =
            String.format(ExceptionMessages.INVALID_MODE_MESSAGE_FORMAT, TestConstants.INVALID_MODE_INPUT_STRING);

    private TestMessages() {
    }
}
