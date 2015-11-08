package com.synchronoss.counter.server;

import com.synchronoss.counter.constants.CounterConstants;
import com.synchronoss.counter.messages.CounterServerMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.util.TestingUtilities;

public class CounterServer {
    private static final Logger logger = LoggerFactory.getLogger(CounterServer.class);
    private static final String APPLICATION_CONTEXT = "classpath:spring/server/applicationContext.xml";

    private final GenericXmlApplicationContext context;

    public CounterServer() {
        context = new GenericXmlApplicationContext();
    }

    public void configure(CommandLinePropertySource commandLinePropertySource) {
        context.getEnvironment().getPropertySources().addFirst(commandLinePropertySource);
    }

    public void configure(int port) {
        System.setProperty(CounterConstants.PORT_PROPERTY_NAME, Integer.toString(port));
    }

    public void startup() {
        logger.info(CounterServerMessages.COUNTER_SERVER_START_MESSAGE);
        context.load(APPLICATION_CONTEXT);
        context.registerShutdownHook();
        context.refresh();
        AbstractServerConnectionFactory abstractServerConnectionFactory =
                context.getBean(AbstractServerConnectionFactory.class);
        TestingUtilities.waitListening(abstractServerConnectionFactory, CounterConstants.START_STOP_SERVER_WAIT_TIME_MILLISECS);
    }

    public void shutdown() {
        logger.info(CounterServerMessages.COUNTER_SERVER_STOP_MESSAGE);
        AbstractServerConnectionFactory abstractServerConnectionFactory =
                context.getBean(AbstractServerConnectionFactory.class);
        context.stop();
        TestingUtilities.waitStopListening(abstractServerConnectionFactory, CounterConstants.START_STOP_SERVER_WAIT_TIME_MILLISECS);
    }

}