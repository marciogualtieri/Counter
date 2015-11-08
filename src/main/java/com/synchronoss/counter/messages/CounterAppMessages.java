package com.synchronoss.counter.messages;

import com.synchronoss.counter.constants.CounterConstants;

public class CounterAppMessages {
    private static final String MISSING_OPTIONAL_ARGUMENT_MESSAGE_FORMAT = "Using default value since optional argument %s is missing.";
    public static final String MISSING_OPTIONAL_ARGUMENT_PORT_MESSAGE = String.format(MISSING_OPTIONAL_ARGUMENT_MESSAGE_FORMAT,
            "--port=<port number>.");
    public static final String MISSING_OPTIONAL_ARGUMENT_HOST_MESSAGE = String.format(MISSING_OPTIONAL_ARGUMENT_MESSAGE_FORMAT,
            "--host=<host name>.");
    public static final String STOP_SERVER_MESSAGE =
            "Type <" + CounterConstants.EXIT_APPLICATION_TOKEN + "> to stop the server.";
    public static final String RESPONSE_FROM_SERVER_MESSAGE_FORMAT = "Response: [%s].";

    private CounterAppMessages() {
    }
}