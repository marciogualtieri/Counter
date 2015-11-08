package com.synchronoss.counter.test.constants;

public class TestConstants {

    public static final int PORT = 5678;
    public static final String INVALID_OPERATION_INPUT_STRING = "not_a_valid_operation";
    public static final String INVALID_MODE_INPUT_STRING = "not_a_valid_mode";
    public static final String[] APPLICATION_ARGUMENTS_WITH_INVALID_MODE =
            {"--mode=" + INVALID_MODE_INPUT_STRING, "--port=1234"};
    public static final String[] APPLICATION_ARGUMENTS_WITH_INVALID_OPERATION =
            {"--mode=C", "--port=1234", "--operation=" + INVALID_OPERATION_INPUT_STRING};
    public static final String[] APPLICATION_ARGUMENTS_WITH_MISSING_MODE =
            {"--port=1234", "--operation=G"};
    public static final String[] APPLICATION_ARGUMENTS_WITH_MISSING_OPERATION =
            {"--mode=C", "--port=1234"};
    public static final int CLIENT_THREADS_POOL_SIZE = 25;
    public static final int CLIENT_THREADS_WAIT_TIME_MILLISECS = 180000;
    public static final String CONFIGURATION_PROPERTIES_FILE_NAME_AND_PATH =
            "src/test/resources/spring/configuration.properties";

    private TestConstants() {
    }
}
