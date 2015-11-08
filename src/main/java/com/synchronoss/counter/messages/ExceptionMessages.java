package com.synchronoss.counter.messages;

public class ExceptionMessages {
    private static final String MODES_MESSAGE = "S(erver) or C(lient)";
    public static final String MISSING_MODE_MESSAGE =
            "Mandatory argument --mode=<" + MODES_MESSAGE + "> is missing.";
    public static final String INVALID_MODE_MESSAGE_FORMAT =
            "[%s] is an invalid mode, valid operations are " + MODES_MESSAGE + ".";
    private static final String OPERATIONS_MESSAGE =
            "R(eset counter), I(ncrement counter), D(ecrement counter) or G(et counter)";
    public static final String MISSING_OPERATION_MESSAGE =
            "Mandatory argument --operation=<" + OPERATIONS_MESSAGE + "> is missing.";
    public static final String INVALID_OPERATION_MESSAGE_FORMAT =
            "[%s] is an invalid operation, valid operations are " + OPERATIONS_MESSAGE + ".";

    private ExceptionMessages() {
    }
}
