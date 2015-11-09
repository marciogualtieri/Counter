package com.synchronoss.counter;

import com.synchronoss.counter.client.CounterClient;
import com.synchronoss.counter.constants.CounterConstants;
import com.synchronoss.counter.enums.Mode;
import com.synchronoss.counter.enums.Operation;
import com.synchronoss.counter.exceptions.IllegalModeException;
import com.synchronoss.counter.exceptions.IllegalOperationException;
import com.synchronoss.counter.messages.CounterAppMessages;
import com.synchronoss.counter.server.CounterServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import java.util.Scanner;

public class CounterApp {
    private static final Logger logger = LoggerFactory.getLogger(CounterApp.class);

    public static void main(String[] args) {
        CounterApp counterApp = new CounterApp();
        counterApp.cli(args);
    }

    public void cli(String[] args) {
        CommandLinePropertySource commandLinePropertySource =
                parsesInputArgsIntoCommandLinePropertySource(args);
        Mode mode = getModeFromCommandLinePropertySource(commandLinePropertySource);
        if (mode.equals(Mode.C)) {
            createConfigureAndRunClient(commandLinePropertySource);
        } else {
            createConfigureAndRunServer(commandLinePropertySource);
        }
    }

    private void createConfigureAndRunClient(CommandLinePropertySource commandLinePropertySource) throws IllegalOperationException {
        CounterClient client = new CounterClient();
        client.configure(commandLinePropertySource);
        client.open();
        String operation = commandLinePropertySource.getProperty(CounterConstants.OPERATION_PROPERTY_NAME);
        try {
            String response = client.sendOperation(Operation.valueOf(operation));
            System.out.println(String.format(CounterAppMessages.RESPONSE_FROM_SERVER_MESSAGE_FORMAT, response));
        } catch (IllegalArgumentException e) {
            throw new IllegalOperationException(operation);
        } finally {
            client.close();
        }
    }

    private void createConfigureAndRunServer(CommandLinePropertySource commandLinePropertySource) {
        CounterServer server = new CounterServer();
        server.configure(commandLinePropertySource);
        server.startup();
        waitForUserToExitApplication();
    }

    private void waitForUserToExitApplication() {
        final Scanner scanner = new Scanner(System.in);
        System.out.println(CounterAppMessages.STOP_SERVER_MESSAGE);
        while (true) {
            String input = scanner.nextLine();
            if (CounterConstants.EXIT_APPLICATION_TOKEN.equals(input.trim())) {
                System.exit(0);
            }
        }
    }

    private CommandLinePropertySource parsesInputArgsIntoCommandLinePropertySource(String[] args)
            throws IllegalArgumentException {
        CommandLinePropertySource commandLinePropertySource = new SimpleCommandLinePropertySource(args);
        Mode mode = getModeFromCommandLinePropertySource(commandLinePropertySource);
        checkIfCommonPropertiesExist(commandLinePropertySource);
        if (mode.equals(Mode.C)) {
            checkIfClientExclusivePropertiesExist(commandLinePropertySource);
        }
        return commandLinePropertySource;
    }

    private void checkIfCommonPropertiesExist(CommandLinePropertySource commandLinePropertySource) {
        if (!commandLinePropertySource.containsProperty(CounterConstants.MODE_PROPERTY_NAME)) {
            throw new IllegalModeException();
        }
        if (!commandLinePropertySource.containsProperty(CounterConstants.PORT_PROPERTY_NAME)) {
            logger.info(CounterAppMessages.MISSING_OPTIONAL_ARGUMENT_PORT_MESSAGE);
        }
    }

    private void checkIfClientExclusivePropertiesExist(CommandLinePropertySource commandLinePropertySource) {
        if (!commandLinePropertySource.containsProperty(CounterConstants.HOST_PROPERTY_NAME)) {
            logger.info(CounterAppMessages.MISSING_OPTIONAL_ARGUMENT_HOST_MESSAGE);
        }
        if (!commandLinePropertySource.containsProperty(CounterConstants.OPERATION_PROPERTY_NAME)) {
            throw new IllegalOperationException();
        }
    }

    private Mode getModeFromCommandLinePropertySource(CommandLinePropertySource commandLinePropertySource) {
        String mode = commandLinePropertySource.getProperty(CounterConstants.MODE_PROPERTY_NAME);
        if (mode == null) {
            throw new IllegalModeException();
        }
        try {
            return Mode.valueOf(mode);
        } catch (IllegalArgumentException e) {
            throw new IllegalModeException(mode);
        }
    }

}
