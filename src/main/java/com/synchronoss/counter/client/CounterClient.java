package com.synchronoss.counter.client;

import com.synchronoss.counter.constants.CounterConstants;
import com.synchronoss.counter.enums.Operation;
import com.synchronoss.counter.gateways.SimpleGateway;
import com.synchronoss.counter.messages.CounterClientMessages;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;

public class CounterClient {

    private static final Logger logger = LoggerFactory.getLogger(CounterClient.class);
    private static final String APPLICATION_CONTEXT = "classpath:spring/client/applicationContext.xml";

    private final GenericXmlApplicationContext context;

    public CounterClient() {
        context = new GenericXmlApplicationContext();
    }

    public void configure(CommandLinePropertySource commandLinePropertySource) {
        context.getEnvironment().getPropertySources().addFirst(commandLinePropertySource);
    }

    public void configure(String host, int port) {
        System.setProperty(CounterConstants.HOST_PROPERTY_NAME, host);
        System.setProperty(CounterConstants.PORT_PROPERTY_NAME, Integer.toString(port));
    }

    public void configure(String host) {
        System.setProperty(CounterConstants.HOST_PROPERTY_NAME, host);
    }

    public void configure(int port) {
        System.setProperty(CounterConstants.PORT_PROPERTY_NAME, Integer.toString(port));
    }

    public void open() {
        context.load(APPLICATION_CONTEXT);
        String port = getUsedPortFromContext(context);
        String host = getUsedHostFromContext(context);
        logger.debug(CounterClientMessages.COUNTER_CLIENT_OPEN_MESSAGE_FORMAT, host, port);
        context.registerShutdownHook();
        context.refresh();
    }

    public void close() {
        logger.debug(CounterClientMessages.COUNTER_CLIENT_CLOSE_MESSAGE);
        context.close();
    }

    public String sendOperation(Operation operation) {
        logger.debug(CounterClientMessages.COUNTER_CLIENT_SEND_MESSAGE_FORMAT, operation);
        final SimpleGateway gateway = context.getBean(SimpleGateway.class);
        return gateway.send(operation.toString());
    }

    public String increment() {
        return sendOperation(Operation.I);
    }

    public String decrement() {
        return sendOperation(Operation.D);
    }

    public String reset() {
        return sendOperation(Operation.R);
    }

    public String evaluate() {
        return sendOperation(Operation.E);
    }

    private String getUsedPortFromContext(GenericXmlApplicationContext context) {
        String port = context.getEnvironment().getProperty(CounterConstants.PORT_PROPERTY_NAME);
        String defaultPort = context.getEnvironment().getProperty(CounterConstants.DEFAULT_PORT_PROPERTY_NAME);
        return StringUtils.defaultIfBlank(port, defaultPort);
    }

    private String getUsedHostFromContext(GenericXmlApplicationContext context) {
        String host = context.getEnvironment().getProperty(CounterConstants.HOST_PROPERTY_NAME);
        String defaultHost = context.getEnvironment().getProperty(CounterConstants.DEFAULT_HOST_PROPERTY_NAME);
        return StringUtils.defaultIfBlank(host, defaultHost);
    }
}
