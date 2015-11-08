package com.synchronoss.counter.messages;

public class CounterClientMessages {
    public static final String COUNTER_CLIENT_OPEN_MESSAGE_FORMAT = "Opening counter client for host [{}] and port [{}]...";
    public static final String COUNTER_CLIENT_CLOSE_MESSAGE = "Closing counter client...";
    public static final String COUNTER_CLIENT_SEND_MESSAGE_FORMAT = "Sending operation [{}]...";

    private CounterClientMessages() {
    }
}
