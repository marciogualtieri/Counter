package com.synchronoss.counter.messages;

public class CounterServiceMessages {
    public static final String COUNTER_OPERATION_MESSAGE_FORMAT = "Counter updated to value [{}] by operation [{}].";
    public static final String COUNTER_LOADED_MESSAGE_FORMAT = "Counter value [{}] loaded from file [{}].";
    public static final String COUNTER_SAVED_MESSAGE_FORMAT = "Counter value [{}] saved to file [{}].";

    private CounterServiceMessages() {
    }
}
